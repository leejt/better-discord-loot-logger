package com.betterdiscordlootlogger;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Slf4j
public class DiscordSplitTrackerPanel extends PluginPanel {

    final JTextField npcName = new JTextField();
    final JTextField itemName = new JTextField();
    final JLabel thumbnail = new JLabel();
    final JTextPane submitInfo = new JTextPane();
    final JTextField splitMembers = new JTextField();

    private final Client client;
    public BufferedImage before;

    ImageIcon icon = new ImageIcon();


    @SneakyThrows
    DiscordSplitTrackerPanel(DiscordSplitTrackerPlugin discordSplitTrackerPlugin, Client client) {
        this.client = client;
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setBorder(null);
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{158, 0};
        gridBagLayout.rowHeights = new int[]{176, 27, 0, 185, 1, 161, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

//                                final JLabel submitLabel = new JLabel();
//                                submitLabel.setAutoscrolls(true);
//                                submitLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//                                submitLabel.setLabelFor(this);
//                                submitLabel.setHorizontalAlignment(SwingConstants.CENTER);
//                                submitLabel.setForeground(Color.WHITE);
//                                submitLabel.setFont(new Font("RuneScape", Font.PLAIN, 20));
//                                submitLabel.setText("Submit Loot to Discord");
//                                submitLabel.setBorder(new EmptyBorder(10, 0, 4, 0));
//                                GridBagConstraints gbc_submitLabel = new GridBagConstraints();
//                                gbc_submitLabel.fill = GridBagConstraints.HORIZONTAL;
//                                gbc_submitLabel.gridwidth = 2;
//                                gbc_submitLabel.insets = new Insets(4, 4, 5, 4);
//                                gbc_submitLabel.gridx = 0;
//                                gbc_submitLabel.gridy = 0;
//                                add(submitLabel, gbc_submitLabel);

        JPanel lootPanel = new JPanel();
        lootPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        lootPanel.setFont(new Font("RuneScape", Font.PLAIN, 13));
        lootPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        lootPanel.setBorder(null);
        GridBagConstraints gbc_lootPanel = new GridBagConstraints();
        gbc_lootPanel.ipadx = 1;
        gbc_lootPanel.fill = GridBagConstraints.BOTH;
        gbc_lootPanel.insets = new Insets(8, 8, 8, 8);
        gbc_lootPanel.gridx = 0;
        gbc_lootPanel.gridy = 0;
        add(lootPanel, gbc_lootPanel);
        GridBagLayout gbl_lootPanel = new GridBagLayout();
        gbl_lootPanel.columnWidths = new int[]{158, 0};
        gbl_lootPanel.rowHeights = new int[]{45, 35, 35, 35};
        gbl_lootPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_lootPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        lootPanel.setLayout(gbl_lootPanel);

        JLabel lblLootInfo = new JLabel();
        lblLootInfo.setText("Loot Information");
        lblLootInfo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLootInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLootInfo.setForeground(Color.WHITE);
        lblLootInfo.setFont(new Font("RuneScape", Font.BOLD, 18));
        lblLootInfo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        lblLootInfo.setBorder(new EmptyBorder(4, 0, 4, 0));
        GridBagConstraints gbc_lblLootInfo = new GridBagConstraints();
        gbc_lblLootInfo.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblLootInfo.anchor = GridBagConstraints.SOUTH;
        gbc_lblLootInfo.insets = new Insets(8, 0, 5, 0);
        gbc_lblLootInfo.gridx = 0;
        gbc_lblLootInfo.gridy = 0;
        lootPanel.add(lblLootInfo, gbc_lblLootInfo);
        GridBagConstraints gbc_itemName = new GridBagConstraints();
        gbc_itemName.fill = GridBagConstraints.BOTH;
        gbc_itemName.insets = new Insets(4, 4, 5, 4);
        gbc_itemName.gridx = 0;
        gbc_itemName.gridy = 1;
        lootPanel.add(itemName, gbc_itemName);
        itemName.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        itemName.setHorizontalAlignment(SwingConstants.CENTER);
        itemName.setBorder(UIManager.getBorder("FormattedTextField.border"));
        itemName.setForeground(Color.WHITE);

        itemName.setEditable(true);
        itemName.setToolTipText("Item Name");
        itemName.setBackground(Color.DARK_GRAY);
        itemName.setText("Item Name");
        GridBagConstraints gbc_npcName = new GridBagConstraints();
        gbc_npcName.fill = GridBagConstraints.BOTH;
        gbc_npcName.insets = new Insets(4, 4, 5, 4);
        gbc_npcName.gridx = 0;
        gbc_npcName.gridy = 2;
        lootPanel.add(npcName, gbc_npcName);
        npcName.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        npcName.setHorizontalAlignment(SwingConstants.CENTER);
        npcName.setBorder(UIManager.getBorder("FormattedTextField.border"));
        npcName.setForeground(Color.WHITE);

        npcName.setEditable(true);
        npcName.setToolTipText("Boss/NPC Name");
        npcName.setBackground(Color.DARK_GRAY);
        npcName.setText("Boss/NPC Name");
        final JButton clearNames = new JButton("Reset Loot Information");
        GridBagConstraints gbc_clearNames = new GridBagConstraints();
        gbc_clearNames.gridx = 0;
        gbc_clearNames.gridy = 3;
        lootPanel.add(clearNames, gbc_clearNames);
        clearNames.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        clearNames.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        clearNames.setForeground(Color.WHITE);
        clearNames.addActionListener(e -> {
            itemName.setText("Item Name");
            npcName.setText("Boss/NPC Name");
        });
        npcName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (npcName.getText().equals("Boss/NPC Name")) {
                    npcName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (npcName.getText().isEmpty()) {
                    npcName.setText("Boss/NPC Name");
                }
            }
        });
        itemName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (itemName.getText().equals("Item Name")) {
                    itemName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (itemName.getText().isEmpty()) {
                    itemName.setText("Item Name");
                }
            }
        });

        JPanel splitPanel = new JPanel();
        splitPanel.setBorder(null);
        splitPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        GridBagConstraints gbc_splitPanel = new GridBagConstraints();
        gbc_splitPanel.fill = GridBagConstraints.BOTH;
        gbc_splitPanel.insets = new Insets(8, 8, 8, 8);
        gbc_splitPanel.gridx = 0;
        gbc_splitPanel.gridy = 1;
        add(splitPanel, gbc_splitPanel);
        GridBagLayout gbl_splitPanel = new GridBagLayout();
        gbl_splitPanel.columnWidths = new int[]{75, 140, 0};
        gbl_splitPanel.rowHeights = new int[]{45, 35, 35, 0, 0};
        gbl_splitPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_splitPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        splitPanel.setLayout(gbl_splitPanel);

        JLabel lblSplitInfo = new JLabel();
        lblSplitInfo.setHorizontalTextPosition(SwingConstants.CENTER);
        GridBagConstraints gbc_lblSplitInfo = new GridBagConstraints();
        gbc_lblSplitInfo.gridwidth = 2;
        gbc_lblSplitInfo.anchor = GridBagConstraints.SOUTH;
        gbc_lblSplitInfo.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblSplitInfo.insets = new Insets(8, 0, 5, 0);
        gbc_lblSplitInfo.gridx = 0;
        gbc_lblSplitInfo.gridy = 0;
        splitPanel.add(lblSplitInfo, gbc_lblSplitInfo);
        lblSplitInfo.setText("Split Information");
        lblSplitInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSplitInfo.setForeground(Color.WHITE);
        lblSplitInfo.setFont(new Font("RuneScape", Font.BOLD, 18));
        lblSplitInfo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        lblSplitInfo.setBorder(new EmptyBorder(4, 0, 4, 0));


        final JTextField splitValue = new JTextField();
        GridBagConstraints gbc_splitValue = new GridBagConstraints();
        gbc_splitValue.gridwidth = 2;
        gbc_splitValue.fill = GridBagConstraints.BOTH;
        gbc_splitValue.insets = new Insets(4, 4, 5, 4);
        gbc_splitValue.gridx = 0;
        gbc_splitValue.gridy = 1;
        splitPanel.add(splitValue, gbc_splitValue);
        splitValue.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        splitValue.setHorizontalAlignment(SwingConstants.CENTER);
        splitValue.setBorder(UIManager.getBorder("FormattedTextField.border"));
        splitValue.setForeground(Color.WHITE);
        splitValue.setEditable(true);
        splitValue.setToolTipText("Split Value");
        splitValue.setBackground(Color.DARK_GRAY);
        splitValue.setText("Split Value (Per-Player)");


        GridBagConstraints gbc_splitMembers = new GridBagConstraints();
        gbc_splitMembers.gridwidth = 2;
        gbc_splitMembers.insets = new Insets(4, 4, 5, 4);
        gbc_splitMembers.fill = GridBagConstraints.BOTH;
        gbc_splitMembers.gridx = 0;
        gbc_splitMembers.gridy = 2;
        splitPanel.add(splitMembers, gbc_splitMembers);
        splitMembers.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        splitMembers.setHorizontalAlignment(SwingConstants.CENTER);
        splitMembers.setBorder(UIManager.getBorder("FormattedTextField.border"));
        splitMembers.setForeground(Color.WHITE);
        splitMembers.setEditable(true);
        splitMembers.setToolTipText("Split with");
        splitMembers.setBackground(Color.DARK_GRAY);
        splitMembers.setText("Split with (Player Names)");

        //submit button
        final JButton submitLoot = new JButton("Submit");
        GridBagConstraints gbc_submitLoot = new GridBagConstraints();
        gbc_submitLoot.fill = GridBagConstraints.HORIZONTAL;
        gbc_submitLoot.insets = new Insets(0, 6, 6, 4);
        gbc_submitLoot.gridx = 0;
        gbc_submitLoot.gridy = 3;
        splitPanel.add(submitLoot, gbc_submitLoot);
        submitLoot.setSize(new Dimension(70, 30));
        submitLoot.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        submitLoot.setForeground(Color.WHITE);
        final JButton reTakeScreenshot = new JButton("Take Screenshot");
        GridBagConstraints gbc_reTakeScreenshot = new GridBagConstraints();
        gbc_reTakeScreenshot.anchor = GridBagConstraints.EAST;
        gbc_reTakeScreenshot.gridwidth = 1;
        gbc_reTakeScreenshot.insets = new Insets(0, 0, 6, 6);
        gbc_reTakeScreenshot.gridx = 1;
        gbc_reTakeScreenshot.gridy = 3;
        splitPanel.add(reTakeScreenshot, gbc_reTakeScreenshot);
        reTakeScreenshot.setSize(new Dimension(1, 30));
        reTakeScreenshot.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        reTakeScreenshot.setForeground(Color.WHITE);
        reTakeScreenshot.addActionListener(e -> discordSplitTrackerPlugin.dataToPanel(npcName.getText(), itemName.getText()));
        submitLoot.addActionListener(e -> {
            if (!splitMembers.getText().contains("Split with") && !splitValue.getText().contains("Split Value") && !itemName.getText().contains("Item Name")) {
                submitInfo.setFocusable(true);
                try {
                    discordSplitTrackerPlugin.sendMessage(itemName.getText(), null, npcName.getText(), splitValue.getText(), "Split Loot", ApiTools.getWikiIcon(itemName.getText()), splitMembers.getText(), false);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                submitInfo.setText("Sent screenshot with the following:\nSplit Value: " + splitValue.getText() + "\nSplit Members: " + splitMembers.getText());
                npcName.setText("Boss/NPC Name");
                itemName.setText("Item Name");
                splitValue.setText("Split Value (Per-Player)");
                splitMembers.setText("Split with (Player Names)");
                submitInfo.setVisible(true);
            } else if (npcName.getText().equals("Boss/NPC Name") && itemName.getText().equals("Item Name") && splitMembers.getText().contains("Split with") && splitValue.getText().contains("Split Value")) {
                submitInfo.setText("Could not get Item or Split Information. Please try again after receiving loot.");
                submitInfo.setEditable(false);
            } else if (npcName.getText().equals("Boss/NPC Name") && itemName.getText().equals("Item Name") && splitMembers.getText().contains("Split with") || splitValue.getText().contains("Split Value")) {
                discordSplitTrackerPlugin.sendMessage(itemName.getText(), null, "", "", "Manual Upload", "", "", false);
                submitInfo.setText("Sent screenshot with the following:\nNPC name: " + npcName.getText() + "\nItem Name: " + itemName.getText());
                npcName.setText("Boss/NPC Name");
                itemName.setText("Item Name");
                splitValue.setText("Split Value (Per-Player)");
                splitMembers.setText("Split with (Player Names)");
                submitInfo.setVisible(true);
            } else {
                try {
                    discordSplitTrackerPlugin.sendMessage(itemName.getText(), null, npcName.getText(), "", "Manual Upload", ApiTools.getWikiIcon(itemName.getText()), "", false);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                npcName.setText("Boss/NPC Name");
                itemName.setText("Item Name");
                splitValue.setText("Split Value (Per-Player)");
                splitMembers.setText("Split with (Player Names)");
                submitInfo.setVisible(true);
                submitInfo.setText("Sent screenshot with the following:\nNPC name: " + npcName.getText() + "\nItem Name: " + itemName.getText() + "\nSplit Value: " + splitValue.getText() + "\nSplit Members: " + splitMembers.getText());
            }

        });
        splitMembers.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (splitMembers.getText().equals("Split with (Player Names)")) {
                    splitMembers.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (splitMembers.getText().isEmpty()) {
                    splitMembers.setText("Split with (Player Names)");
                }
            }
        });
        splitValue.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (splitValue.getText().equals("Split Value (Per-Player)")) {
                    splitValue.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (splitValue.getText().isEmpty()) {
                    splitValue.setText("Split Value (Per-Player)");
                }
            }
        });
        submitInfo.setFont(new Font("RuneScape", Font.PLAIN, 15));
        submitInfo.setPreferredSize(new Dimension(0, 0));
        submitInfo.setVisible(false);
        submitInfo.setFocusable(false);
        submitInfo.setBackground(ColorScheme.DARK_GRAY_COLOR);
        submitInfo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        submitInfo.setForeground(Color.WHITE);
        submitInfo.setEditable(false);
        GridBagConstraints gbc_submitInfo = new GridBagConstraints();
        gbc_submitInfo.anchor = GridBagConstraints.SOUTH;
        gbc_submitInfo.fill = GridBagConstraints.HORIZONTAL;
        gbc_submitInfo.gridx = 0;
        gbc_submitInfo.gridy = 2;
        gbc_submitInfo.insets = new Insets(4, 10, 5, 10);
        add(submitInfo, gbc_submitInfo);


        thumbnail.setAutoscrolls(true);
        thumbnail.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        thumbnail.setForeground(Color.WHITE);
        thumbnail.setIcon(icon);
        GridBagConstraints gbc_thumbnail = new GridBagConstraints();
        gbc_thumbnail.anchor = GridBagConstraints.SOUTH;
        gbc_thumbnail.insets = new Insets(6, 6, 6, 6);
        gbc_thumbnail.gridx = 0;
        gbc_thumbnail.gridy = 5;
        add(thumbnail, gbc_thumbnail);
    }


    public void panelOverride(String bossName, String itemDropped, BufferedImage screenshot) {
        if (!(npcName.getText() == "") && !(itemName.getText() == "")) {
            npcName.setText(bossName);
            itemName.setText(itemDropped);
            submitInfo.setFocusable(true);
            submitInfo.setVisible(true);
            if (itemName.getText().contains("Item Name")) {
                submitInfo.setText("Failed to get Item Info, please wait until you've received loot.");
                return;
            } else {
                submitInfo.setText("Found drop: " + itemDropped + " from " + bossName + ".\nFill out split info above and click submit to send to Discord.");
            }
        }
        thumbnail.setIcon(new ImageIcon(scaleImage(screenshot, 213, 120)));
        thumbnail.setHorizontalAlignment(SwingConstants.LEFT);
        before = screenshot;
    }

    public BufferedImage scaleImage(BufferedImage screenshot, int width, int height) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D resultGraphics = (Graphics2D) result.getGraphics();
        resultGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        resultGraphics.drawImage(screenshot, 0, 0, width, height, null);
        resultGraphics.dispose();
        return result;
    }

//    public void getClanMembers() {
//        SwingUtilities.invokeLater(() -> {
//            JComboBox memberList = new JComboBox();
//            memberList.setEditable(true);
//            GridBagConstraints gbc_memberList = new GridBagConstraints();
//            gbc_memberList.gridwidth = 1;
//            gbc_memberList.insets = new Insets(4, 4, 5, 4);
//            gbc_memberList.fill = GridBagConstraints.BOTH;
//            gbc_memberList.gridx = 0;
//            gbc_memberList.gridy = 3;
//            memberList.setForeground(Color.WHITE);
//            memberList.revalidate();
//            memberList.repaint();
//            add(memberList, gbc_memberList);
//            JButton btnAddMember = new JButton("Add to Split");
//            GridBagConstraints gbc_btnAddMember = new GridBagConstraints();
//            gbc_btnAddMember.gridx = 1;
//            gbc_btnAddMember.gridy = 3;
//            btnAddMember.setForeground(Color.WHITE);
//            add(btnAddMember, gbc_btnAddMember);
//            btnAddMember.addActionListener(e -> {
//                if (splitMembers.getText().contains("Split with")) {
//                    splitMembers.setText(memberList.getItemAt(0).toString());
//                } else {
//                    splitMembers.setText(splitMembers.getText() + ", " + memberList.getItemAt(0).toString());
//                }
//            });
//        });
//
//
//    }

    public void toggleRefreshButton() {
        SwingUtilities.invokeLater(() -> {
            JPanel womPanel = new JPanel();
            if (!womPanel.isVisible()) {
            } else {
                womPanel.setFont(new Font("RuneScape", Font.PLAIN, 13));
                womPanel.setBorder(null);
                womPanel.setBackground(new Color(30, 30, 30));
                womPanel.setAlignmentY(0.0f);
                GridBagConstraints gbc_womPanel = new GridBagConstraints();
                gbc_womPanel.anchor = GridBagConstraints.NORTH;
                gbc_womPanel.insets = new Insets(8, 8, 8, 8);
                gbc_womPanel.fill = GridBagConstraints.HORIZONTAL;
                gbc_womPanel.gridx = 0;
                gbc_womPanel.gridy = 3;
                add(womPanel, gbc_womPanel);
                GridBagLayout gbl_womPanel = new GridBagLayout();
                gbl_womPanel.columnWidths = new int[]{158, 0, 0};
                gbl_womPanel.rowHeights = new int[]{45, 0, 35, 0};
                gbl_womPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
                gbl_womPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
                womPanel.setLayout(gbl_womPanel);

                JLabel lblWiseOldMan = new JLabel();
                lblWiseOldMan.setText("WiseOldMan");
                lblWiseOldMan.setHorizontalTextPosition(SwingConstants.CENTER);
                lblWiseOldMan.setHorizontalAlignment(SwingConstants.CENTER);
                lblWiseOldMan.setForeground(Color.WHITE);
                lblWiseOldMan.setFont(new Font("RuneScape", Font.BOLD, 18));
                lblWiseOldMan.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                lblWiseOldMan.setBorder(new EmptyBorder(4, 0, 4, 0));
                GridBagConstraints gbc_lblWiseOldMan = new GridBagConstraints();
                gbc_lblWiseOldMan.gridwidth = 2;
                gbc_lblWiseOldMan.fill = GridBagConstraints.HORIZONTAL;
                gbc_lblWiseOldMan.anchor = GridBagConstraints.SOUTH;
                gbc_lblWiseOldMan.insets = new Insets(8, 0, 5, 5);
                gbc_lblWiseOldMan.gridx = 0;
                gbc_lblWiseOldMan.gridy = 0;
                womPanel.add(lblWiseOldMan, gbc_lblWiseOldMan);

//        JComboBox groupComboBox = new JComboBox();
//        groupComboBox.setEditable(true);
//        groupComboBox.setMaximumRowCount(20);
//        GridBagConstraints gbc_groupComboBox = new GridBagConstraints();
//        gbc_groupComboBox.fill = GridBagConstraints.HORIZONTAL;
//        gbc_groupComboBox.insets = new Insets(4, 4, 5, 4);
//        gbc_groupComboBox.gridx = 0;
//        gbc_groupComboBox.gridy = 1;
//        womPanel.add(groupComboBox, gbc_groupComboBox);

                JLabel lblGetGroupId = new JLabel("Get List of Members by Group ID:");
                lblGetGroupId.setForeground(Color.WHITE);
                GridBagConstraints gbc_lblGetGroupId = new GridBagConstraints();
                gbc_lblGetGroupId.gridwidth = 2;
                gbc_lblGetGroupId.insets = new Insets(0, 0, 5, 5);
                gbc_lblGetGroupId.gridx = 0;
                gbc_lblGetGroupId.gridy = 1;
                womPanel.add(lblGetGroupId, gbc_lblGetGroupId);
                
                JComboBox<Object> groupComboBox = null;
                try {
                    groupComboBox = new JComboBox<>(Objects.requireNonNull(Objects.requireNonNull(ApiTools.getWomGroupId(requireNonNull(client.getLocalPlayer().getName())))));
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                groupComboBox.setEditable(true);
                GridBagConstraints gbc_groupComboBox = new GridBagConstraints();
                gbc_groupComboBox.fill = GridBagConstraints.HORIZONTAL;
                gbc_groupComboBox.insets = new Insets(4, 4, 5, 4);
                gbc_groupComboBox.gridx = 0;
                gbc_groupComboBox.gridy = 2;
                womPanel.add(groupComboBox, gbc_groupComboBox);
                JButton btnRefresh = new JButton("Search");
                btnRefresh.setForeground(Color.WHITE);
                btnRefresh.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
                gbc_btnRefresh.insets = new Insets(4, 4, 5, 4);
                gbc_btnRefresh.gridx = 1;
                gbc_btnRefresh.gridy = 2;
                womPanel.add(btnRefresh, gbc_btnRefresh);
                JComboBox<Object> finalGroupComboBox = groupComboBox;
                btnRefresh.addActionListener(e -> {
                    new JComboBox<>();
                    JComboBox<Object> memberList;
//                memberList.revalidate();
//                memberList.repaint();
                    try {
                        memberList = new JComboBox<>(Objects.requireNonNull(ApiTools.getGroupMembers(Integer.parseInt(requireNonNull(finalGroupComboBox.getSelectedItem()).toString()))));
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        if(!lblGetGroupId.getText().contains("Got Members")) lblGetGroupId.setText(String.format("Got Members of Group %s", ApiTools.getClanName(Integer.parseInt(requireNonNull(finalGroupComboBox.getSelectedItem()).toString()))));
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    GridBagConstraints gbc_memberList = new GridBagConstraints();
                    gbc_memberList.insets = new Insets(4, 4, 5, 4);
                    gbc_memberList.fill = GridBagConstraints.BOTH;
                    gbc_memberList.gridx = 0;
                    gbc_memberList.gridy = 3;
                    memberList.setForeground(Color.WHITE);
                    womPanel.add(memberList, gbc_memberList);
                    JButton btnAddMember = new JButton("Add to Split");
                    GridBagConstraints gbc_btnAddMember = new GridBagConstraints();
                    gbc_btnAddMember.gridx = 1;
                    gbc_btnAddMember.gridy = 3;
                    btnAddMember.setForeground(Color.WHITE);
                    womPanel.add(btnAddMember, gbc_btnAddMember);
                    JComboBox<Object> finalMemberList = memberList;
                    btnAddMember.addActionListener(f -> {
                        if (splitMembers.getText().contains("Split with")) {
                            splitMembers.setText(requireNonNull(finalMemberList.getSelectedItem()).toString());
                        } else {
                            if (splitMembers.getText().contains((CharSequence) requireNonNull(finalMemberList.getSelectedItem()))) {
                                return;
                            }
                            splitMembers.setText(splitMembers.getText() + ", " + requireNonNull(finalMemberList.getSelectedItem()));
                        }
                    });
                });
//            {
//                JComboBox<Object> groupComboBox = null;
//                try {
//                    groupComboBox = new JComboBox<>(Objects.requireNonNull(Objects.requireNonNull(ApiTools.getWomGroupId(requireNonNull(client.getLocalPlayer().getName())))));
//                } catch (IOException | InterruptedException ex) {
//                    throw new RuntimeException(ex);
//                }
//                groupComboBox.setEditable(true);
//                groupComboBox.setMaximumRowCount(20);
//                GridBagConstraints gbc_groupComboBox = new GridBagConstraints();
//                gbc_groupComboBox.fill = GridBagConstraints.HORIZONTAL;
//                gbc_groupComboBox.insets = new Insets(4, 4, 5, 4);
//                gbc_groupComboBox.gridx = 0;
//                gbc_groupComboBox.gridy = 1;
//                womPanel.add(groupComboBox, gbc_groupComboBox);
////            try {
////                getWomGroupId(requireNonNull(client.getLocalPlayer().getName()));
////            } catch (IOException | InterruptedException ex) {
////                throw new RuntimeException(ex);
////            }
//            }


//
//            JComboBox memberComboBox = new JComboBox();
//            memberComboBox.setEditable(true);
//        memberComboBox.setMaximumRowCount(20);
//        GridBagConstraints gbc_memberComboBox = new GridBagConstraints();
//        gbc_memberComboBox.fill = GridBagConstraints.HORIZONTAL;
//
//        gbc_memberComboBox.insets = new Insets(4, 4, 5, 4);
//        gbc_memberComboBox.gridx = 0;
//        gbc_memberComboBox.gridy = 2;
//        womPanel.add(memberComboBox, gbc_memberComboBox);

//        JButton btnAddMember = new JButton("Add to Split");
//        GridBagConstraints gbc_btnAddMember = new GridBagConstraints();
//        gbc_btnAddMember.gridx = 1;
//        gbc_btnAddMember.gridy = 2;
//        btnAddMember.setForeground(Color.WHITE);
//        womPanel.add(btnAddMember, gbc_btnAddMember);

            }
        });
    }


}

