import xml.etree.ElementTree as ET
import re
import tkinter as tk
from tkinter import filedialog
import os

def process_dat_file(file_path):
    """
    处理.dat文件的主要函数
    """
    try:
        # 读取XML文件
        tree = ET.parse(file_path)
        root = tree.getroot()
        
        # 定义区域信息关键词
        region_keywords = ['Japan', 'USA', 'Europe', 'China']
        
        # 处理每个machine标签
        for machine in root.findall('.//machine'):
            # 处理comment标签
            comment_elem = machine.find('comment')
            region_to_add = None
            
            if comment_elem is not None and comment_elem.text:
                # 去除[CHS]或[CHT]
                comment_text = comment_elem.text
                comment_text = re.sub(r'\[CHS\]|\[CHT\]', '', comment_text)
                comment_elem.text = comment_text
                
                # 查找区域信息
                region_match = re.search(r'\((.*?)\)', comment_text)
                if region_match:
                    region_content = region_match.group(1)
                    # 检查是否包含区域关键词
                    for keyword in region_keywords:
                        if keyword in region_content:
                            region_to_add = f" ({region_content})"
                            break
            
            # 处理name属性
            if 'name' in machine.attrib:
                original_name = machine.attrib['name']
                # 去除前7个字符
                if len(original_name) > 7:
                    new_name = original_name[7:]
                    
                    # 如果有区域信息需要添加
                    if region_to_add:
                        # 在文件扩展名之前插入区域信息，确保前面有空格
                        if '.' in new_name:
                            # 找到最后一个点号的位置
                            dot_pos = new_name.rfind('.')
                            # 在点号前插入区域信息
                            new_name = new_name[:dot_pos] + region_to_add + new_name[dot_pos:]
                        else:
                            # 如果没有扩展名，直接添加到末尾
                            new_name += region_to_add
                    
                    machine.set('name', new_name)
            
            # 处理rom标签的name属性
            rom_elem = machine.find('rom')
            if rom_elem is not None and 'name' in rom_elem.attrib:
                original_rom_name = rom_elem.attrib['name']
                # 去除前7个字符
                if len(original_rom_name) > 7:
                    new_rom_name = original_rom_name[7:]
                    
                    # 如果有区域信息需要添加
                    if region_to_add:
                        # 在文件扩展名之前插入区域信息，确保前面有空格
                        if '.' in new_rom_name:
                            # 找到最后一个点号的位置
                            dot_pos = new_rom_name.rfind('.')
                            # 在点号前插入区域信息
                            new_rom_name = new_rom_name[:dot_pos] + region_to_add + new_rom_name[dot_pos:]
                        else:
                            # 如果没有扩展名，直接添加到末尾
                            new_rom_name += region_to_add
                    
                    rom_elem.set('name', new_rom_name)
        
        # 写回原文件
        tree.write(file_path, encoding='UTF-8', xml_declaration=True)
        print(f"文件处理完成并已保存: {file_path}")
        
    except ET.ParseError as e:
        print(f"XML解析错误: {e}")
    except Exception as e:
        print(f"处理文件时发生错误: {e}")

def select_and_process_file():
    """
    选择文件并处理的函数
    """
    # 创建文件选择对话框
    root = tk.Tk()
    root.withdraw()  # 隐藏主窗口
    
    # 选择.dat文件
    file_path = filedialog.askopenfilename(
        title="选择.dat文件",
        filetypes=[("DAT files", "*.dat"), ("All files", "*.*")]
    )
    
    if file_path:
        print(f"已选择文件: {file_path}")
        process_dat_file(file_path)
    else:
        print("未选择文件")
    
    root.destroy()

if __name__ == "__main__":
    select_and_process_file()