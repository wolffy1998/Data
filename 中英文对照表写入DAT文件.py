import os
import sys
import logging
import tkinter as tk
from tkinter import filedialog
import xml.etree.ElementTree as ET
from xml.etree.ElementTree import Element, SubElement

# 1. 获取程序自身所在的目录（兼容脚本/EXE两种运行模式）
def get_program_dir():
    if hasattr(sys, 'frozen'):  # 判断是否为EXE（PyInstaller标记）
        program_path = os.path.dirname(sys.executable)
    else:
        program_path = os.path.dirname(os.path.abspath(__file__))  # 脚本所在目录
    return program_path

# 2. 配置日志：写入程序目录下的log.txt
program_dir = get_program_dir()
log_path = os.path.join(program_dir, "log.txt")  # 日志文件完整路径

# 配置logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler(log_path, encoding='utf-8', mode='w'),  # 每次启动清理之前的日志
        logging.StreamHandler()  # 同时打印到控制台
    ]
)

def select_file(title, filetypes):
    """弹窗选择文件并返回文件路径"""
    root = tk.Tk()
    root.withdraw()  # 隐藏主窗口
    file_path = filedialog.askopenfilename(title=title, filetypes=filetypes)
    root.destroy()
    return file_path

def load_key_value_pairs(txt_path):
    """从TXT文件加载key-value键值对"""
    key_value = {}
    try:
        with open(txt_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
        
        for line_num, line in enumerate(lines, 1):
            line = line.strip()
            if not line:
                continue
            # 按制表符分割
            parts = line.split('\t')
            if len(parts) != 2:
                logging.warning(f"TXT文件第{line_num}行格式错误，跳过此行")
                continue
            key, value = parts[0].strip(), parts[1].strip()
            key_value[key] = value
            logging.info(f"加载键值对: {key} -> {value}")
    
    except Exception as e:
        logging.error(f"加载TXT文件失败: {str(e)}")
        raise
    
    logging.info(f"成功加载{len(key_value)}组键值对")
    return key_value

def process_xml(dat_path, key_value, xpath):
    """处理XML文件，替换匹配的标签内容并插入注释标签"""
    try:
        # 解析XML文件
        tree = ET.parse(dat_path)
        root = tree.getroot()
        
        # 查找所有匹配的节点
        nodes = root.findall(xpath)
        logging.info(f"在XML中找到{len(nodes)}个匹配'{xpath}'的节点")
        
        modified_count = 0
        comment_added = 0
        
        for node in nodes:
            original_text = node.text
            if original_text in key_value:
                # 记录原始值
                old_value = original_text
                # 替换为对应的值
                new_value = key_value[old_value]
                node.text = new_value
                modified_count += 1
                logging.info(f"替换内容: '{old_value}' -> '{new_value}'")
                
                # 在同级节点中插入comment标签
                parent = node.getparent()
                if parent is not None:
                    # 检查是否已存在comment标签
                    has_comment = any(child.tag == 'comment' for child in parent)
                    if not has_comment:
                        comment = SubElement(parent, 'comment')
                        comment.text = old_value
                        comment_added += 1
                        logging.info(f"添加comment标签: {old_value}")
        
        # 保存修改后的XML
        tree.write(dat_path, encoding='utf-8', xml_declaration=True)
        logging.info(f"XML文件处理完成，共修改{modified_count}处，添加{comment_added}个comment标签")
        
        return modified_count, comment_added
    
    except Exception as e:
        logging.error(f"处理XML文件失败: {str(e)}")
        raise

def main():
    logging.info("程序启动")
    
    try:
        # 1. 选择TXT文件
        txt_path = select_file("选择包含键值对的TXT文件", [("文本文件", "*.txt"), ("所有文件", "*.*")])
        if not txt_path:
            logging.warning("未选择TXT文件，程序退出")
            return
        logging.info(f"选择的TXT文件: {txt_path}")
        
        # 加载键值对
        key_value = load_key_value_pairs(txt_path)
        if not key_value:
            logging.warning("未加载到任何有效键值对，程序退出")
            return
        
        # 2. 选择DAT文件
        dat_path = select_file("选择XML格式的DAT文件", [("DAT文件", "*.dat"), ("所有文件", "*.*")])
        if not dat_path:
            logging.warning("未选择DAT文件，程序退出")
            return
        logging.info(f"选择的DAT文件: {dat_path}")
        
        # 3. 获取用户输入的标签名称
        xpath = input("请输入要匹配的标签路径（例如：game/description/text()）: ").strip()
        if not xpath:
            logging.warning("未输入标签路径，程序退出")
            return
        logging.info(f"用户输入的标签路径: {xpath}")
        
        # 处理XML文件
        modified, comments = process_xml(dat_path, key_value, xpath)
        
        # 统计信息
        logging.info("\n===== 处理统计 =====")
        logging.info(f"总键值对数量: {len(key_value)}")
        logging.info(f"匹配并修改的节点数量: {modified}")
        logging.info(f"添加的comment标签数量: {comments}")
        logging.info("====================")
        
        print("处理完成，详情请查看log.txt")
    
    except Exception as e:
        logging.error(f"程序出错: {str(e)}", exc_info=True)
        print(f"程序出错: {str(e)}，详情请查看log.txt")
    
    logging.info("程序结束")

if __name__ == "__main__":
    main()