import os
import logging
import tkinter as tk
from tkinter import filedialog, messagebox
from lxml import etree
import sys
from collections import defaultdict

# 1. 获取程序自身所在的目录
def get_program_dir():
    if hasattr(sys, 'frozen'):
        program_path = os.path.dirname(sys.executable)
    else:
        program_path = os.path.dirname(os.path.abspath(__file__))
    return program_path

# 2. 配置日志
program_dir = get_program_dir()
log_path = os.path.join(program_dir, "log.txt")

# 清理之前的日志文件
if os.path.exists(log_path):
    os.remove(log_path)

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler(log_path, encoding='utf-8'),
        logging.StreamHandler()
    ]
)

class DATProcessor:
    def __init__(self):
        self.dat_file_path = None
        self.stats = defaultdict(int)
        
    def select_mode(self):
        """选择操作模式"""
        while True:
            print("\n请选择操作模式：")
            print("1 - 添加标签")
            print("2 - 删除标签")
            choice = input("请输入选择（1或2）: ").strip()
            if choice in ['1', '2']:
                return choice
            else:
                print("输入无效，请重新输入！")
    
    def select_dat_file(self):
        """选择dat文件"""
        root = tk.Tk()
        root.withdraw()
        file_path = filedialog.askopenfilename(
            title="选择dat文件",
            filetypes=[("DAT files", "*.dat"), ("All files", "*.*")]
        )
        root.destroy()
        
        if not file_path:
            logging.error("未选择dat文件，程序退出")
            return False
            
        self.dat_file_path = file_path
        logging.info(f"选择的dat文件: {self.dat_file_path}")
        return True
    
    def get_tag_path_from_user(self):
        """获取用户输入的标签路径"""
        print("\n请输入标签路径:")
        print("示例1: game - 在根标签下添加/删除game标签")
        print("示例2: game/rom - 在所有game标签下添加/删除rom标签")
        print("示例3: game/description - 在所有game标签下添加/删除description标签")
        tag_path = input("请输入标签路径: ").strip()
        
        if not tag_path:
            logging.error("未输入标签路径，程序退出")
            return None
            
        return tag_path
    
    def process_add_mode(self, tag_path):
        """处理添加标签模式"""
        try:
            # 解析XML文件
            parser = etree.XMLParser(remove_blank_text=True)
            tree = etree.parse(self.dat_file_path, parser)
            root = tree.getroot()
            
            logging.info(f"根标签: {root.tag}")
            logging.info(f"开始添加标签: {tag_path}")
            
            # 分割标签路径
            tags = tag_path.split('/')
            processed_count = 0
            
            if len(tags) == 1:
                # 在根标签下添加标签
                processed_count = self.add_tags_to_root(tree, root, tags[0])
            else:
                # 在指定父标签下添加子标签
                processed_count = self.add_child_tags(tree, root, tags)
            
            if processed_count > 0:
                # 配置新添加的标签
                self.configure_new_tags(tree, root, tag_path, tags)
                
                logging.info(f"添加标签完成！共处理{processed_count}个位置")
                return True
            else:
                logging.warning("没有找到合适的父标签来添加新标签")
                return False
                
        except Exception as e:
            logging.error(f"添加标签失败: {e}")
            return False
    
    def add_tags_to_root(self, tree, root, tag_name):
        """在根标签下添加标签"""
        # 检查是否已存在该标签
        existing_tags = root.findall(tag_name)
        if existing_tags:
            logging.info(f"根标签下已存在 {len(existing_tags)} 个 {tag_name} 标签")
            target_elements = existing_tags
        else:
            # 创建新标签
            new_tag = etree.SubElement(root, tag_name)
            target_elements = [new_tag]
            logging.info(f"在根标签下创建 {tag_name} 标签")
            self.stats['added_root_tags'] += 1
        
        # 保存文件
        tree.write(self.dat_file_path, 
                  encoding='utf-8', 
                  pretty_print=True, 
                  xml_declaration=True)
        
        return len(target_elements)
    
    def add_child_tags(self, tree, root, tags):
        """在指定父标签下添加子标签"""
        parent_path = '/'.join(tags[:-1])
        child_tag = tags[-1]
        
        # 查找所有父标签
        parent_elements = root.xpath(f'.//{parent_path}')
        logging.info(f"找到 {len(parent_elements)} 个 {parent_path} 元素")
        
        processed_count = 0
        target_elements = []
        
        for parent_element in parent_elements:
            # 检查是否已存在该子标签
            existing_child = parent_element.find(child_tag)
            if existing_child is not None:
                target_elements.append(existing_child)
                continue
                
            # 创建新的子标签
            new_child = etree.SubElement(parent_element, child_tag)
            target_elements.append(new_child)
            processed_count += 1
            logging.info(f"在 {parent_path} 下创建 {child_tag} 标签")
        
        if processed_count > 0:
            # 保存文件
            tree.write(self.dat_file_path, 
                      encoding='utf-8', 
                      pretty_print=True, 
                      xml_declaration=True)
            
            self.stats['added_child_tags'] += processed_count
            logging.info(f"成功在 {len(parent_elements)} 个父标签下添加了 {child_tag} 标签")
        
        return len(target_elements)
    
    def configure_new_tags(self, tree, root, tag_path, tags):
        """配置新添加的标签"""
        if len(tags) == 1:
            # 根标签下的标签
            target_elements = root.findall(tags[0])
        else:
            # 子标签
            target_elements = root.xpath(f'.//{tag_path}')
        
        logging.info(f"开始配置 {len(target_elements)} 个标签")
        
        while True:
            print("\n请选择为标签添加内容：")
            print("1 - 添加属性值")
            print("2 - 添加文本内容")
            print("3 - 完成配置")
            choice = input("请输入选择（1、2或3）: ").strip()
            
            if choice == '1':
                self.add_attributes_to_tags(tree, target_elements)
            elif choice == '2':
                self.add_text_to_tags(tree, target_elements)
            elif choice == '3':
                logging.info("标签配置完成")
                break
            else:
                print("输入无效，请重新输入！")
    
    def add_attributes_to_tags(self, tree, target_elements):
        """为标签添加属性"""
        attr_name = input("请输入属性名: ").strip()
        if not attr_name:
            print("属性名不能为空！")
            return
        
        attr_value = input("请输入属性值: ").strip()
        
        for element in target_elements:
            element.set(attr_name, attr_value)
        
        # 保存文件
        tree.write(self.dat_file_path, 
                  encoding='utf-8', 
                  pretty_print=True, 
                  xml_declaration=True)
        
        logging.info(f"为 {len(target_elements)} 个标签添加属性 {attr_name}=\"{attr_value}\"")
        self.stats['added_attributes'] += len(target_elements)
        print(f"成功为 {len(target_elements)} 个标签添加属性 {attr_name}=\"{attr_value}\"")
    
    def add_text_to_tags(self, tree, target_elements):
        """为标签添加文本内容"""
        text_content = input("请输入文本内容: ").strip()
        
        for element in target_elements:
            element.text = text_content
        
        # 保存文件
        tree.write(self.dat_file_path, 
                  encoding='utf-8', 
                  pretty_print=True, 
                  xml_declaration=True)
        
        logging.info(f"为 {len(target_elements)} 个标签添加文本内容: \"{text_content}\"")
        self.stats['added_text'] += len(target_elements)
        print(f"成功为 {len(target_elements)} 个标签添加文本内容: \"{text_content}\"")
    
    def process_delete_mode(self, tag_path):
        """处理删除标签模式"""
        try:
            # 解析XML文件
            parser = etree.XMLParser(remove_blank_text=True)
            tree = etree.parse(self.dat_file_path, parser)
            root = tree.getroot()
            
            logging.info(f"根标签: {root.tag}")
            logging.info(f"开始删除标签: {tag_path}")
            
            # 查找所有匹配的标签
            elements_to_delete = root.xpath(f'.//{tag_path}')
            logging.info(f"找到 {len(elements_to_delete)} 个 {tag_path} 标签")
            
            if len(elements_to_delete) == 0:
                logging.warning(f"未找到匹配的标签: {tag_path}")
                messagebox.showwarning("警告", f"未找到匹配的标签: {tag_path}")
                return False
            
            # 确认删除
            confirm = input(f"确认删除 {len(elements_to_delete)} 个 {tag_path} 标签吗？(y/n): ").strip().lower()
            if confirm != 'y':
                logging.info("用户取消删除操作")
                return False
            
            # 删除标签
            deleted_count = 0
            for element in elements_to_delete:
                parent = element.getparent()
                if parent is not None:
                    parent.remove(element)
                    deleted_count += 1
            
            # 保存文件
            tree.write(self.dat_file_path, 
                      encoding='utf-8', 
                      pretty_print=True, 
                      xml_declaration=True)
            
            logging.info(f"删除标签完成！共删除{deleted_count}个标签")
            self.stats['deleted_tags'] += deleted_count
            return True
            
        except Exception as e:
            logging.error(f"删除标签失败: {e}")
            return False
    
    def print_statistics(self):
        """打印统计信息"""
        logging.info("=" * 50)
        logging.info("操作统计:")
        if self.stats['added_root_tags'] > 0:
            logging.info(f"添加根标签: {self.stats['added_root_tags']} 个")
        if self.stats['added_child_tags'] > 0:
            logging.info(f"添加子标签: {self.stats['added_child_tags']} 个")
        if self.stats['added_attributes'] > 0:
            logging.info(f"添加属性: {self.stats['added_attributes']} 次")
        if self.stats['added_text'] > 0:
            logging.info(f"添加文本: {self.stats['added_text']} 次")
        if self.stats['deleted_tags'] > 0:
            logging.info(f"删除标签: {self.stats['deleted_tags']} 个")
        logging.info("=" * 50)

def main():
    """主程序"""
    logging.info("程序启动")
    
    processor = DATProcessor()
    
    try:
        # 步骤1：选择模式
        mode = processor.select_mode()
        mode_text = "添加标签" if mode == '1' else "删除标签"
        logging.info(f"选择模式: {mode_text}")
        
        # 步骤2：选择dat文件
        if not processor.select_dat_file():
            return
        
        # 步骤3：获取标签路径
        tag_path = processor.get_tag_path_from_user()
        if not tag_path:
            return
        
        # 步骤4/5：处理文件
        if mode == '1':
            success = processor.process_add_mode(tag_path)
        else:
            success = processor.process_delete_mode(tag_path)
        
        if success:
            # 打印统计信息
            processor.print_statistics()
            
            messagebox.showinfo("完成", "处理完成！请查看日志文件了解详细信息。")
        else:
            messagebox.showerror("错误", "处理失败，请查看日志文件了解详细信息。")
            
    except Exception as e:
        logging.error(f"程序执行出错: {e}")
        messagebox.showerror("错误", f"程序执行出错: {e}")

if __name__ == "__main__":
    main()