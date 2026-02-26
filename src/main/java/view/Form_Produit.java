package view;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import controller.ProduitController;
import dto.ProduitDTO;

public class Form_Produit extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfLibelle, tfPrix, tfStock;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnNouveau;
    private JComboBox<String> comboBox;
    private List<ProduitDTO> listeProduits;
    private ProduitDTO produitSelectionne;

    // Couleurs modernes
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Bleu
    private final Color SUCCESS_COLOR = new Color(46, 204, 113);     // Vert
    private final Color WARNING_COLOR = new Color(241, 196, 15);     // Jaune
    private final Color DANGER_COLOR = new Color(231, 76, 60);       // Rouge
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Gris clair
    private final Color PANEL_COLOR = Color.WHITE;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Form_Produit frame = new Form_Produit();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void effacer() {
        tfLibelle.setText("");
        tfPrix.setText("");
        tfStock.setText("");
        comboBox.setSelectedIndex(0);
        produitSelectionne = null;
    }

    public void activerChamps() {
        tfLibelle.setEnabled(true);
        tfPrix.setEnabled(true);
        tfStock.setEnabled(true);
        tfLibelle.setBackground(Color.WHITE);
        tfPrix.setBackground(Color.WHITE);
        tfStock.setBackground(Color.WHITE);
    }

    public void desactiverChamps() {
        tfLibelle.setEnabled(false);
        tfPrix.setEnabled(false);
        tfStock.setEnabled(false);
        tfLibelle.setBackground(new Color(240, 240, 240));
        tfPrix.setBackground(new Color(240, 240, 240));
        tfStock.setBackground(new Color(240, 240, 240));
    }

    public boolean isFloat(String s) {
        try { Float.parseFloat(s); return true; }
        catch (NumberFormatException e) { return false; }
    }

    public boolean isInt(String s) {
        try { Integer.parseInt(s); return true; }
        catch (NumberFormatException e) { return false; }
    }

    public void chargerCombo() {
        listeProduits = new ProduitController().getAllProduit();
        comboBox.removeAllItems();
        comboBox.addItem("-- Choisir un produit --");
        for (ProduitDTO p : listeProduits) {
            comboBox.addItem(p.getLibelle());
        }
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(bgColor.darker(), 1, true),
                new EmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Effet hover
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(80, 80, 80));
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
    }

    public Form_Produit() {
        setResizable(false);
        setTitle("Gestion des produits");
        setBounds(100, 100, 650, 460);
        contentPane = new JPanel();
        contentPane.setBackground(BACKGROUND_COLOR);
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Panel recherche
        JPanel panelRecherche = new JPanel();
        panelRecherche.setBackground(PANEL_COLOR);
        panelRecherche.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        panelRecherche.setBounds(20, 10, 590, 80);
        contentPane.add(panelRecherche);
        panelRecherche.setLayout(null);

        JLabel lblChoisir = new JLabel("Produit");
        styleLabel(lblChoisir);
        lblChoisir.setBounds(15, 30, 60, 20);
        panelRecherche.add(lblChoisir);

        comboBox = new JComboBox<>();
        styleComboBox(comboBox);
        comboBox.setBounds(85, 30, 350, 30);
        panelRecherche.add(comboBox);

        btnNouveau = new JButton("Nouveau");
        styleButton(btnNouveau, WARNING_COLOR, Color.white);
        btnNouveau.setBounds(455, 30, 110, 30);
        panelRecherche.add(btnNouveau);

        // Panel formulaire
        JPanel panelForm = new JPanel();
        panelForm.setBackground(PANEL_COLOR);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panelForm.setBounds(20, 100, 590, 250);
        contentPane.add(panelForm);
        panelForm.setLayout(null);

        JLabel lblLibelle = new JLabel("Libellé");
        styleLabel(lblLibelle);
        lblLibelle.setBounds(20, 25, 80, 20);
        panelForm.add(lblLibelle);

        tfLibelle = new JTextField();
        styleTextField(tfLibelle);
        tfLibelle.setBounds(120, 22, 350, 30);
        tfLibelle.setEnabled(false);
        tfLibelle.setBackground(new Color(240, 240, 240));
        panelForm.add(tfLibelle);

        JLabel lblPrix = new JLabel("Prix (€)");
        styleLabel(lblPrix);
        lblPrix.setBounds(20, 70, 80, 20);
        panelForm.add(lblPrix);

        tfPrix = new JTextField();
        styleTextField(tfPrix);
        tfPrix.setBounds(120, 67, 350, 30);
        tfPrix.setEnabled(false);
        tfPrix.setBackground(new Color(240, 240, 240));
        panelForm.add(tfPrix);

        JLabel lblStock = new JLabel("Stock");
        styleLabel(lblStock);
        lblStock.setBounds(20, 115, 80, 20);
        panelForm.add(lblStock);

        tfStock = new JTextField();
        styleTextField(tfStock);
        tfStock.setBounds(120, 112, 350, 30);
        tfStock.setEnabled(false);
        tfStock.setBackground(new Color(240, 240, 240));
        panelForm.add(tfStock);

        // Panel pour les boutons d'action
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(PANEL_COLOR);
        panelBoutons.setBounds(20, 170, 550, 60);
        panelForm.add(panelBoutons);
        panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnAjouter = new JButton("Ajouter");
        styleButton(btnAjouter, SUCCESS_COLOR, Color.WHITE);
        btnAjouter.setPreferredSize(new Dimension(120, 35));
        btnAjouter.setEnabled(false);
        panelBoutons.add(btnAjouter);

        btnModifier = new JButton("Modifier");
        styleButton(btnModifier, PRIMARY_COLOR, Color.WHITE);
        btnModifier.setPreferredSize(new Dimension(120, 35));
        btnModifier.setEnabled(false);
        panelBoutons.add(btnModifier);

        btnSupprimer = new JButton("Supprimer");
        styleButton(btnSupprimer, DANGER_COLOR, Color.WHITE);
        btnSupprimer.setPreferredSize(new Dimension(120, 35));
        btnSupprimer.setEnabled(false);
        panelBoutons.add(btnSupprimer);

        // Charger combo au démarrage
        chargerCombo();

        // ---- Actions ----

        // Sélection dans la liste déroulante
        comboBox.addActionListener(e -> {
            int index = comboBox.getSelectedIndex();
            if (index > 0) {
                produitSelectionne = listeProduits.get(index - 1);
                tfLibelle.setText(produitSelectionne.getLibelle());
                tfPrix.setText(String.valueOf(produitSelectionne.getPrix()));
                tfStock.setText(String.valueOf(produitSelectionne.getQtstock()));
                activerChamps();
                btnModifier.setEnabled(true);
                btnSupprimer.setEnabled(true);
                btnAjouter.setEnabled(false);
            } else {
                effacer();
                desactiverChamps();
                btnModifier.setEnabled(false);
                btnSupprimer.setEnabled(false);
            }
        });

        // Nouveau
        btnNouveau.addActionListener(e -> {
            effacer();
            activerChamps();
            btnAjouter.setEnabled(true);
            btnModifier.setEnabled(false);
            btnSupprimer.setEnabled(false);
            comboBox.setEnabled(false);
        });

        // Ajouter
        btnAjouter.addActionListener(e -> {
            if (!isFloat(tfPrix.getText()) || !isInt(tfStock.getText())) {
                JOptionPane.showMessageDialog(null,
                        "Prix doit être un nombre réel et Stock un entier",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            ProduitDTO dto = new ProduitDTO();
            dto.setLibelle(tfLibelle.getText());
            dto.setPrix(Float.parseFloat(tfPrix.getText()));
            dto.setQtstock(Integer.parseInt(tfStock.getText()));
            new ProduitController().ajouterProduit(dto);
            JOptionPane.showMessageDialog(null,
                    "✅ Produit ajouté avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
            chargerCombo();
            effacer();
            desactiverChamps();
            btnAjouter.setEnabled(false);
            comboBox.setEnabled(true);
        });

        // Modifier
        btnModifier.addActionListener(e -> {
            if (!isFloat(tfPrix.getText()) || !isInt(tfStock.getText())) {
                JOptionPane.showMessageDialog(null,
                        "Prix doit être un nombre réel et Stock un entier",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            ProduitDTO dto = new ProduitDTO();
            dto.setLibelle(tfLibelle.getText());
            dto.setPrix(Float.parseFloat(tfPrix.getText()));
            dto.setQtstock(Integer.parseInt(tfStock.getText()));
            new ProduitController().modifierProduit(dto, produitSelectionne.getId());
            JOptionPane.showMessageDialog(null,
                    "✏️ Produit modifié avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
            chargerCombo();
            effacer();
            desactiverChamps();
            btnModifier.setEnabled(false);
            btnSupprimer.setEnabled(false);
        });

        // Supprimer
        btnSupprimer.addActionListener(e -> {
            int choix = JOptionPane.showConfirmDialog(null,
                    "⚠️ Êtes-vous sûr de vouloir supprimer ce produit ?\nCette action est irréversible.",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (choix == JOptionPane.YES_OPTION) {
                boolean res = new ProduitController().supprimerProduit(produitSelectionne.getId());
                if (res) {
                    JOptionPane.showMessageDialog(null,
                            "🗑️ Produit supprimé avec succès !",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    chargerCombo();
                    effacer();
                    desactiverChamps();
                    btnModifier.setEnabled(false);
                    btnSupprimer.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "❌ Impossible de supprimer ce produit (déjà commandé)",
                            "Échec de la suppression",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Centrer la fenêtre
        setLocationRelativeTo(null);
    }
}