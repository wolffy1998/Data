import json
import os
import tkinter as tk
from tkinter import filedialog, messagebox
from xml.etree.ElementTree import Element, SubElement, tostring
from xml.dom import minidom

def convert_rdb_to_dat(rdb_path):
    try:
        with open(rdb_path, "r", encoding="utf-8") as f:
            rdb_data = json.load(f)

        # 判断结构，RetroArch新版RDB通常是 { "database": { "games": [...] } } 或 { "games": [...] }
        if "games" in rdb_data:
            games = rdb_data["games"]
        elif "database" in rdb_data and "games" in rdb_data["database"]:
            games = rdb_data["database"]["games"]
        else:
            messagebox.showerror("错误", "未在JSON中找到 'games' 字段，格式不正确。")
            return

        # 构建XML结构
        root = Element("datafile")
        header = SubElement(root, "header")
        SubElement(header, "name").text = os.path.splitext(os.path.basename(rdb_path))[0]
        SubElement(header, "description").text = "Converted from RetroArch RDB"
        SubElement(header, "category").text = "Console"

        for game in games:
            name = game.get("name") or "Unknown"
            size = str(game.get("size", 0))
            crc = (game.get("crc") or "").upper()
            md5 = (game.get("md5") or "").lower()
            sha1 = (game.get("sha1") or "").lower()

            g = SubElement(root, "game", name=name)
            SubElement(g, "description").text = name
            SubElement(g, "rom",
                       name=f"{name}.gba",
                       size=size,
                       crc=crc,
                       md5=md5,
                       sha1=sha1
                       )

        # 格式化XML
        xml_str = minidom.parseString(tostring(root)).toprettyxml(indent="  ")
        dat_path = os.path.splitext(rdb_path)[0] + ".dat"

        with open(dat_path, "w", encoding="utf-8") as f:
            f.write(xml_str)

        messagebox.showinfo("完成", f"已成功生成 DAT 文件：\n{dat_path}")

    except Exception as e:
        messagebox.showerror("转换失败", f"错误信息：{str(e)}")

def main():
    root = tk.Tk()
    root.withdraw()

    messagebox.showinfo("RDB 转 DAT", "请选择要转换的 RetroArch RDB JSON 文件")
    rdb_path = filedialog.askopenfilename(
        title="选择 RDB JSON 文件",
        filetypes=[("JSON 文件", "*.json"), ("所有文件", "*.*")]
    )

    if not rdb_path:
        messagebox.showwarning("取消", "未选择文件，程序已退出。")
        return

    convert_rdb_to_dat(rdb_path)

if __name__ == "__main__":
    main()
