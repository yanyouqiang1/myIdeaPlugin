package com.yyq;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ArthasWindow {
    private JPanel mainPanel;
    private JButton refreshBtn;
    private JList jpsList;
    private JLabel mesLable;

    public ArthasWindow() {
        refreshBtn.addActionListener(e -> freshList());
    }

    private void freshList() {
        List<String> javaList = runCMD("jps");
        jpsList.setListData(javaList.toArray());
        jpsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //双击事件
                    int selectedIndex = jpsList.getSelectedIndex();
                    String javaPs = getPs(javaList.get(selectedIndex));
                    operationPs(javaPs);

                    System.out.println(selectedIndex);
                }
            }
        });
        jpsList.validate();
    }

    private void operationPs(String javaPs) {
        setClipboardString("as "+javaPs);
        mesLable.setText("内容已复制");
    }

    /**
     * 把文本设置到剪贴板（复制）
     */
    public static void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

    private String getPs(String s) {
        return s.split(" ")[0];
    }


    public static List<String> runCMD(String command) {
        List<String> result = new LinkedList<>();
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public JPanel getContent() {
        return mainPanel;
    }

}
