package com.yyq.arthas;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.yyq.util.ClipboardUtil;
import com.yyq.util.CmdUtil;

import java.util.Arrays;
import java.util.List;

public class ArthasCmdGenerate extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        List<String> jpsList = CmdUtil.runCMD("jps");

        JBPopupFactory.getInstance()
                .createPopupChooserBuilder(jpsList)
                .setItemChosenCallback(choseIndex -> {
                    String psId = getPsId(choseIndex);
                    ClipboardUtil.setClipboardString("as " + psId);
                    Notifications.Bus.notify(new Notification("ArthasCmdGenerate","cmd copied",NotificationType.INFORMATION));
                })
                .setTitle("Select an item")
                .setAdText("jps")
                .createPopup()
                .showInFocusCenter();

    }

    private String getPsId(String choseIndex) {
        return choseIndex.split(" ")[0];
    }
}
