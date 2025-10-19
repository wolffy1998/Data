import os
import shutil
import xml.etree.ElementTree as ET
from tkinter import Tk, filedialog

def select_file(title, filetypes):
    """使用文件选择对话框选择文件"""
    root = Tk()
    root.withdraw()  # 隐藏主窗口
    file_path = filedialog.askopenfilename(title=title, filetypes=filetypes)
    return file_path

def select_directory(title):
    """使用文件夹选择对话框选择文件夹"""
    root = Tk()
    root.withdraw()  # 隐藏主窗口
    dir_path = filedialog.askdirectory(title=title)
    return dir_path

def get_release_descriptions(dat_path):
    """从DAT文件中获取releaseNumber与description的对应关系"""
    try:
        tree = ET.parse(dat_path)
        root = tree.getroot()
        
        # 查找一级标签datafile
        datafile = root.find('datafile')
        if datafile is None:
            print("DAT文件中未找到datafile标签")
            return {}
            
        # 查找二级标签machine
        machine = datafile.find('machine')
        if machine is None:
            print("DAT文件中未找到machine标签")
            return {}
            
        # 收集releaseNumber与description的对应关系
        descriptions = {}
        for release in machine.findall('release'):
            num = release.get('releaseNumber')
            desc = release.get('description')
            if num and desc:
                descriptions[num] = desc
                
        return descriptions
        
    except Exception as e:
        print(f"读取DAT文件出错: {str(e)}")
        return {}

def process_xml_and_copy_images(xml_path, dat_path, img_folder_path):
    """处理XML文件并复制图片"""
    # 获取releaseNumber与description的对应关系
    release_descriptions = get_release_descriptions(dat_path)
    if not release_descriptions:
        print("未获取到有效的release信息，无法继续处理")
        return
    
    try:
        # 解析XML文件
        tree = ET.parse(xml_path)
        root = tree.getroot()
        
        # 查找一级标签dat
        dat_tag = root.find('dat')
        if dat_tag is None:
            print("XML文件中未找到dat标签")
            return
            
        # 查找二级标签games
        games_tag = dat_tag.find('games')
        if games_tag is None:
            print("XML文件中未找到games标签")
            return
        
        # 创建标题图和预览图目录（上级目录下）
        parent_dir = os.path.dirname(img_folder_path)
        title_dir = os.path.join(parent_dir, "标题图目录")
        preview_dir = os.path.join(parent_dir, "预览图文件夹")
        
        os.makedirs(title_dir, exist_ok=True)
        os.makedirs(preview_dir, exist_ok=True)
        print(f"标题图目录: {title_dir}")
        print(f"预览图目录: {preview_dir}")
        
        # 处理每个game标签
        for game in games_tag.findall('game'):
            image_number = game.get('imageNumber')
            if not image_number:
                continue
                
            try:
                # 转换为整数进行范围判断
                num = int(image_number)
                if num <= 0:
                    continue
                    
                # 确定图片所在的子文件夹
                range_start = ((num - 1) // 500) * 500 + 1
                range_end = range_start + 499
                subfolder = f"{range_start}-{range_end}"
                subfolder_path = os.path.join(img_folder_path, subfolder)
                
                if not os.path.exists(subfolder_path):
                    print(f"文件夹不存在: {subfolder_path}，跳过处理")
                    continue
                
                # 检查是否有对应的description
                if image_number not in release_descriptions:
                    print(f"未找到imageNumber {image_number} 对应的description，跳过处理")
                    continue
                
                new_name = release_descriptions[image_number] + ".png"
                
                # 处理a和b两种图片
                for suffix in ['a', 'b']:
                    src_filename = f"{image_number}{suffix}.png"
                    src_path = os.path.join(subfolder_path, src_filename)
                    
                    if not os.path.exists(src_path):
                        print(f"图片不存在: {src_path}，跳过处理")
                        continue
                    
                    # 确定目标路径
                    if suffix == 'a':
                        dest_path = os.path.join(title_dir, new_name)
                    else:
                        dest_path = os.path.join(preview_dir, new_name)
                    
                    # 复制文件
                    shutil.copy2(src_path, dest_path)
                    print(f"已复制: {src_path} -> {dest_path}")
                    
            except ValueError:
                print(f"无效的imageNumber值: {image_number}，跳过处理")
            except Exception as e:
                print(f"处理imageNumber {image_number} 时出错: {str(e)}")
                
    except Exception as e:
        print(f"读取XML文件出错: {str(e)}")

def main():
    print("请选择XML文件")
    xml_path = select_file("选择XML文件", [("XML文件", "*.xml"), ("所有文件", "*.*")])
    if not xml_path:
        print("未选择XML文件，程序退出")
        return
        
    print("请选择DAT文件")
    dat_path = select_file("选择DAT文件", [("DAT文件", "*.dat"), ("所有文件", "*.*")])
    if not dat_path:
        print("未选择DAT文件，程序退出")
        return
        
    print("请选择图片文件夹")
    img_folder_path = select_directory("选择图片文件夹")
    if not img_folder_path:
        print("未选择图片文件夹，程序退出")
        return
    
    # 执行处理
    process_xml_and_copy_images(xml_path, dat_path, img_folder_path)
    print("处理完成")

if __name__ == "__main__":
    main()