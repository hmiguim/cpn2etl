/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import cpn.Cpn;
import cpn.Stats;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import xml.XMLFactory;
import xml.XMLParser;

/**
 *
 * @author hmg
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    public Main() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(475, 375));
        this.generateLabel.setText("");
        this.openLabel.setText("");
        this.debug.setEditable(false);

        this.generateButton.setEnabled(false);

        openFile.setAcceptAllFileFilterUsed(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        saveFile = new javax.swing.JFileChooser();
        openFile = new javax.swing.JFileChooser();
        openPanel = new javax.swing.JPanel();
        openLabel = new javax.swing.JLabel();
        openButton = new javax.swing.JButton();
        generatePanel = new javax.swing.JPanel();
        generateButton = new javax.swing.JButton();
        generateLabel = new javax.swing.JLabel();
        outputPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        debug = new javax.swing.JTextArea();

        saveFile.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        saveFile.setDialogTitle("Open file ...");
        saveFile.setFileFilter(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("cpn2etl");

        openLabel.setText("sdfsd");

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout openPanelLayout = new javax.swing.GroupLayout(openPanel);
        openPanel.setLayout(openPanelLayout);
        openPanelLayout.setHorizontalGroup(
            openPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(openPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        openPanelLayout.setVerticalGroup(
            openPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, openPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(openPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openLabel)
                    .addComponent(openButton)))
        );

        generateButton.setText("Generate");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        generateLabel.setText("dasds");

        javax.swing.GroupLayout generatePanelLayout = new javax.swing.GroupLayout(generatePanel);
        generatePanel.setLayout(generatePanelLayout);
        generatePanelLayout.setHorizontalGroup(
            generatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(generateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(generateLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        generatePanelLayout.setVerticalGroup(
            generatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateButton)
                    .addComponent(generateLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        outputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

        debug.setColumns(20);
        debug.setRows(5);
        jScrollPane1.setViewportView(debug);

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout.setHorizontalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addContainerGap())
        );
        outputPanelLayout.setVerticalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(openPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(generatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(openPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        File f = openFile();
        try {
            XMLFactory xmlFactory = XMLFactory.newInstance();

            if (f != null) {
                XMLParser xmlParser = xmlFactory.newXMLParser(f);

                Cpn cpn = xmlParser.parse();

                Stats stats = cpn.stats();

                this.debug.append("\n");
                this.debug.append(stats.toString());
            } else {
                this.debug.append("[Error] \n");
            }

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            this.debug.append("\n\n");
        }

    }//GEN-LAST:event_openButtonActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        int returnVal = saveFile.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String file = saveFile.getSelectedFile().getName();

            this.debug.append("[Success] File genareted successfully");
        }
    }//GEN-LAST:event_generateButtonActionPerformed
   
    private File openFile() {

        File file = null;

        FileFilter f = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".xml");
            }

            @Override
            public String getDescription() {
                return "XML Files";
            }
        };

        openFile.addChoosableFileFilter(f);
        openFile.setFileFilter(f);
        int returnVal = openFile.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = openFile.getSelectedFile();
            openLabel.setText("Opened file: " + file.getAbsolutePath());
            this.debug.append("[Success] File opened successfully!\n");
            this.generateButton.setEnabled(true);
        }

        return file;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea debug;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel generateLabel;
    private javax.swing.JPanel generatePanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openButton;
    private javax.swing.JFileChooser openFile;
    private javax.swing.JLabel openLabel;
    private javax.swing.JPanel openPanel;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JFileChooser saveFile;
    // End of variables declaration//GEN-END:variables
}