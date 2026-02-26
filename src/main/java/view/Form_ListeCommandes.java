package view;

import bo.Commande;
import bo.Ligne_Commande;
import controller.CommandeController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

public class Form_ListeCommandes extends JFrame {

    private JTable tableCommandes;
    private JTable tableLignes;
    private List<Commande> listeCommandes;

    // Couleurs modernes
    private final Color PRIMARY_COLOR = new Color(52, 73, 94);        // Gris foncé
    private final Color ACCENT_COLOR = new Color(41, 128, 185);      // Bleu
    private final Color SUCCESS_COLOR = new Color(46, 204, 113);     // Vert
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Gris clair
    private final Color PANEL_COLOR = Color.WHITE;
    private final Color HEADER_BG = new Color(52, 73, 94);           // Gris foncé
    private final Color HEADER_FG = Color.WHITE;                     // Blanc
    private final Color SELECTION_BG = new Color(41, 128, 185, 50);  // Bleu transparent

    public Form_ListeCommandes() {
        setTitle("📋 Gestion des Commandes");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal avec fond
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        // ===== TITRE =====
        JLabel lblTitle = new JLabel("LISTE DES COMMANDES");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(PRIMARY_COLOR);
        lblTitle.setBounds(20, 5, 400, 40);
        mainPanel.add(lblTitle);

        // ===== PANEL COMMANDES =====
        JPanel panelCommandes = new JPanel();
        panelCommandes.setBackground(PANEL_COLOR);
        panelCommandes.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panelCommandes.setBounds(20, 50, 840, 280);
        panelCommandes.setLayout(new BorderLayout());
        mainPanel.add(panelCommandes);

        JLabel lblCommandes = new JLabel("📦 Commandes");
        lblCommandes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCommandes.setForeground(ACCENT_COLOR);
        lblCommandes.setBorder(new EmptyBorder(0, 5, 10, 0));
        panelCommandes.add(lblCommandes, BorderLayout.NORTH);

        // TABLE COMMANDES
        tableCommandes = new JTable();
        styleTable(tableCommandes);

        JScrollPane scroll1 = new JScrollPane(tableCommandes);
        scroll1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scroll1.getViewport().setBackground(Color.WHITE);
        panelCommandes.add(scroll1, BorderLayout.CENTER);

        // ===== PANEL LIGNES =====
        JPanel panelLignes = new JPanel();
        panelLignes.setBackground(PANEL_COLOR);
        panelLignes.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panelLignes.setBounds(20, 350, 840, 280);
        panelLignes.setLayout(new BorderLayout());
        mainPanel.add(panelLignes);

        JLabel lblLignes = new JLabel("📝 Détails de la commande");
        lblLignes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLignes.setForeground(ACCENT_COLOR);
        lblLignes.setBorder(new EmptyBorder(0, 5, 10, 0));
        panelLignes.add(lblLignes, BorderLayout.NORTH);

        // TABLE LIGNES
        tableLignes = new JTable();
        styleTable(tableLignes);

        JScrollPane scroll2 = new JScrollPane(tableLignes);
        scroll2.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scroll2.getViewport().setBackground(Color.WHITE);
        panelLignes.add(scroll2, BorderLayout.CENTER);

        // ===== PANEL INFOS =====
        JPanel panelInfos = new JPanel();
        panelInfos.setBackground(PANEL_COLOR);
        panelInfos.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        panelInfos.setBounds(20, 640, 840, 40);
        panelInfos.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        mainPanel.add(panelInfos);

        JLabel lblInfo1 = new JLabel("🖱️ Cliquez sur une commande pour voir les détails");
        lblInfo1.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo1.setForeground(new Color(100, 100, 100));
        panelInfos.add(lblInfo1);

        JLabel lblInfo2 = new JLabel("💰 Les totaux sont calculés automatiquement");
        lblInfo2.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo2.setForeground(SUCCESS_COLOR);
        panelInfos.add(lblInfo2);

        // Charger les commandes
        chargerCommandes();

        // Ajouter l'écouteur de sélection
        tableCommandes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableCommandes.getSelectedRow();
                if (row >= 0) {
                    afficherLignesAvecCalcul(row);
                }
            }
        });

        // Double-clic pour plus d'infos
        tableCommandes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tableCommandes.getSelectedRow();
                    if (row >= 0) {
                        afficherDetailsCommande(row);
                    }
                }
            }
        });
    }

    // ============================
    // STYLE DES TABLEAUX
    // ============================
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(SELECTION_BG);
        table.setSelectionForeground(Color.BLACK);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // === STYLE DE L'EN-TÊTE - SOLUTION SIMPLE ET EFFICACE ===
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer());
        header.setBackground(HEADER_BG);
        header.setForeground(HEADER_FG);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Renderer pour centrer certaines colonnes
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Renderer pour aligner à droite les montants
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        // Appliquer les renderers après que le modèle soit défini
        table.addPropertyChangeListener("model", evt -> {
            for (int i = 0; i < table.getColumnCount(); i++) {
                String columnName = table.getColumnName(i);
                if (columnName.contains("€") || columnName.contains("Total") ||
                        columnName.contains("Prix") || columnName.contains("Sous-Total")) {
                    table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
                } else if (columnName.contains("Quantité") || columnName.contains("ID") ||
                        columnName.contains("Nb")) {
                    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
            }
        });
    }

    // ============================
    // RENDERER PERSONNALISÉ POUR L'EN-TÊTE
    // ============================
    private class HeaderRenderer extends DefaultTableCellRenderer {
        public HeaderRenderer() {
            setOpaque(true);
            setBackground(HEADER_BG);
            setForeground(HEADER_FG);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setHorizontalAlignment(JLabel.CENTER);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(45, 62, 80), 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Maintenir les couleurs même si sélectionné
            setBackground(HEADER_BG);
            setForeground(HEADER_FG);

            return this;
        }
    }

    // ============================
    // CHARGER LES COMMANDES
    // ============================
    private void chargerCommandes() {
        listeCommandes = new CommandeController().getAllCommandes();

        Vector<String> titres = new Vector<>();
        titres.add("ID");
        titres.add("Date");
        titres.add("Client");
        titres.add("Total (€)");
        titres.add("Nb Articles");

        Vector<Vector<Object>> data = new Vector<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (Commande c : listeCommandes) {
            Vector<Object> row = new Vector<>();
            row.add(String.format("%04d", c.getIdcmd()));

            // Formater la date avec heure
            String dateStr = "";
            if (c.getDatecmd() != null) {
                dateStr = dateFormat.format(c.getDatecmd()) + " à " + timeFormat.format(c.getDatecmd());
            }
            row.add(dateStr);

            row.add(c.getClient().getNom());

            // Calculer le total
            double total = calculerTotalCommande(c);
            row.add(String.format("%.2f €", total));

            // === CORRECTION ICI : Calculer le nombre TOTAL d'articles (somme des quantités) ===
            int nbArticles = calculerNombreTotalArticles(c);
            row.add(nbArticles + " article" + (nbArticles > 1 ? "s" : ""));

            data.add(row);
        }

        DefaultTableModel model = new DefaultTableModel(data, titres) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableCommandes.setModel(model);

        // Ajuster les colonnes
        tableCommandes.getColumnModel().getColumn(0).setPreferredWidth(60);
        tableCommandes.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableCommandes.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableCommandes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableCommandes.getColumnModel().getColumn(4).setPreferredWidth(100);
    }

    // ============================
    // NOUVELLE MÉTHODE : CALCULER LE NOMBRE TOTAL D'ARTICLES
    // ============================
    private int calculerNombreTotalArticles(Commande commande) {
        int total = 0;
        for (Ligne_Commande l : commande.getLignes()) {
            total += l.getQuantite();
        }
        return total;
    }

    // ============================
    // AFFICHER LES LIGNES AVEC CALCUL
    // ============================
    private void afficherLignesAvecCalcul(int index) {
        if (index < 0 || index >= listeCommandes.size()) {
            return;
        }

        Commande commande = listeCommandes.get(index);

        // Mettre à jour le titre du panel
        Component parent = tableLignes.getParent().getParent();
        if (parent instanceof JPanel) {
            JPanel panel = (JPanel) parent;
            Component[] comps = panel.getComponents();
            for (Component comp : comps) {
                if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    int nbArticles = calculerNombreTotalArticles(commande);
                    label.setText("📝 Détails de la commande #" + String.format("%04d", commande.getIdcmd()) +
                            " - " + commande.getClient().getNom() +
                            " (" + nbArticles + " article" + (nbArticles > 1 ? "s" : "") + ")");
                    break;
                }
            }
        }

        Vector<String> titres = new Vector<>();
        titres.add("Produit");
        titres.add("Quantité");
        titres.add("Prix Unitaire");
        titres.add("Sous-Total");

        Vector<Vector<Object>> data = new Vector<>();

        for (Ligne_Commande l : commande.getLignes()) {
            Vector<Object> row = new Vector<>();
            row.add(l.getProduit().getLibelle());
            row.add(l.getQuantite());

            double prix = l.getProduit().getPrix();
            row.add(String.format("%.2f €", prix));

            // Calcul du sous-total
            double sousTotal = l.getQuantite() * prix;
            row.add(String.format("%.2f €", sousTotal));

            data.add(row);
        }

        // Ajouter une ligne de total
        if (!data.isEmpty()) {
            Vector<Object> totalRow = new Vector<>();
            totalRow.add("TOTAL");
            totalRow.add(calculerNombreTotalArticles(commande)); // Afficher le nombre total d'articles
            totalRow.add("");
            totalRow.add(String.format("%.2f €", calculerTotalCommande(commande)));
            data.add(totalRow);
        }

        // Rendre les cellules non éditables
        DefaultTableModel model = new DefaultTableModel(data, titres) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableLignes.setModel(model);

        // Colorer la ligne de total et alterner les couleurs des lignes
        if (!data.isEmpty()) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus,
                                                               int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Style pour la ligne de total (dernière ligne)
                    if (row == table.getRowCount() - 1) {
                        c.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        c.setBackground(new Color(240, 240, 240));
                    } else {
                        c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        // Alternance des couleurs de lignes
                        if (isSelected) {
                            c.setBackground(SELECTION_BG);
                        } else {
                            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));
                        }
                    }

                    // Alignement du texte
                    if (value instanceof String) {
                        String text = (String) value;
                        if (text.contains("€") || text.contains("TOTAL")) {
                            setHorizontalAlignment(JLabel.RIGHT);
                        } else {
                            setHorizontalAlignment(JLabel.LEFT);
                        }
                    } else if (value instanceof Integer) {
                        setHorizontalAlignment(JLabel.CENTER);
                    }

                    return c;
                }
            };

            // Appliquer le renderer à toutes les colonnes
            for (int i = 0; i < tableLignes.getColumnCount(); i++) {
                tableLignes.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        }
    }

    // ============================
    // CALCULER LE TOTAL D'UNE COMMANDE
    // ============================
    private double calculerTotalCommande(Commande commande) {
        double total = 0;
        for (Ligne_Commande l : commande.getLignes()) {
            total += l.getQuantite() * l.getProduit().getPrix();
        }
        return total;
    }

    // ============================
    // AFFICHER LES DÉTAILS COMPLETS
    // ============================
    private void afficherDetailsCommande(int index) {
        if (index < 0 || index >= listeCommandes.size()) {
            return;
        }

        Commande commande = listeCommandes.get(index);
        int nbArticles = calculerNombreTotalArticles(commande);

        String message = String.format(
                "📦 COMMANDE #%04d\n\n" +
                        "👤 Client : %s\n" +
                        "📅 Date : %s\n" +
                        "💰 Total : %.2f €\n\n" +
                        "📝 Articles : %d (au total)\n" +
                        "🔢 Lignes de commande : %d\n",
                commande.getIdcmd(),
                commande.getClient().getNom(),
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(commande.getDatecmd()),
                calculerTotalCommande(commande),
                nbArticles,
                commande.getLignes().size()
        );

        JOptionPane.showMessageDialog(this,
                message,
                "Détails de la commande",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            // Set System Look and Feel for better integration
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Form_ListeCommandes().setVisible(true);
        });
    }
}