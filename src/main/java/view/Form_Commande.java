package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import controller.ClientController;
import controller.CommandeController;
import controller.Ligne_commandeController;
import controller.ProduitController;
import dto.ClientDTO;
import dto.CommandeDTO;
import dto.ProduitDTO;
import dto.Ligne_CommandeDTO;
import exception.ClientNotFoundException;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;


import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

public class Form_Commande extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JComboBox comboBox;
	private JTable table_1;
	private JTextField textField_8;
	private JFormattedTextField formattedTextField;

	// Contrôleurs en tant que variables d'instance (une seule instance pour tout le formulaire)
	private CommandeController commandeController;
	private Ligne_commandeController ligneCommandeController;
	private ProduitController produitController;
	private ClientController clientController;

	private void remplircombo() {
		comboBox.removeAllItems();
		comboBox.addItem("Sélectionner un produit");
		for (ProduitDTO p : produitController.getAllProduit())
			comboBox.addItem(p);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form_Commande frame = new Form_Commande();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Form_Commande() {
		// Initialiser les contrôleurs (une seule instance)
		commandeController = new CommandeController();
		ligneCommandeController = new Ligne_commandeController();
		produitController = new ProduitController();
		clientController = new ClientController();

		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				CommandeDTO dto = commandeController.createCommande();
				remplircombo();
			}
		});

		setTitle("Gestion de Commandes");
		setBounds(100, 100, 764, 724);

		// Création du panneau principal avec layout null
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(null);

		// Création du JScrollPane avec le panneau principal
		JScrollPane scrollPane = new JScrollPane(mainPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Définir le contentPane avec le JScrollPane
		setContentPane(scrollPane);

		// Assigner mainPanel à contentPane pour la compatibilité
		contentPane = mainPanel;

		// Définir une taille préférée pour le panneau principal
		mainPanel.setPreferredSize(new java.awt.Dimension(750, 800));

		JLabel lblNewLabel = new JLabel("Date Commande");
		lblNewLabel.setBounds(39, 60, 145, 16);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(37, 103, 676, 131);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Id Client");
		lblNewLabel_1.setBounds(22, 24, 105, 13);
		panel.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setBounds(122, 21, 89, 19);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton = new JButton("Chercher");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField_1.getText().equals("")) {
					try {
						ClientDTO dto = clientController.getClientDTO(Integer.valueOf(textField_1.getText()));
						textField_2.setText(dto.getNom());
						textField_3.setText(String.valueOf(dto.getCapital()));
						textField_4.setText(dto.getAdresse());
						commandeController.associerClient(dto);
					} catch (ClientNotFoundException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", 0);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "L'ID client doit être un nombre valide!", "Message", 1);
					}
				} else
					JOptionPane.showMessageDialog(null, "Merci d'indiquer un Id valide de client!", "Message", 1);
			}
		});
		btnNewButton.setBounds(247, 20, 117, 21);
		panel.add(btnNewButton);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.BOLD, 14));
		textField_2.setEnabled(false);
		textField_2.setBounds(122, 67, 201, 19);
		panel.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Dialog", Font.BOLD, 14));
		textField_3.setEnabled(false);
		textField_3.setBounds(412, 67, 183, 19);
		panel.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Nom Client");
		lblNewLabel_2.setBounds(22, 73, 105, 13);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Capital");
		lblNewLabel_3.setBounds(333, 70, 69, 13);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Adresse");
		lblNewLabel_4.setBounds(22, 108, 89, 13);
		panel.add(lblNewLabel_4);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.BOLD, 14));
		textField_4.setEnabled(false);
		textField_4.setBounds(121, 105, 474, 19);
		panel.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Nouvelle Commande");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setBounds(241, 23, 339, 13);
		contentPane.add(lblNewLabel_5);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(39, 256, 674, 119);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("Quantité");
		lblNewLabel_6.setBounds(444, 68, 45, 13);
		panel_1.add(lblNewLabel_6);

		textField_5 = new JTextField();
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setText("1");
		textField_5.setBounds(499, 65, 64, 19);
		panel_1.add(textField_5);
		textField_5.setColumns(10);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Sélectionner un produit" }));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox.getSelectedItem() != comboBox.getItemAt(0)) {
						ProduitDTO dto = (ProduitDTO) comboBox.getSelectedItem();
						textField_6.setText(dto.getLibelle());
						textField_7.setText(String.valueOf(dto.getPrix()));
					} else {
						textField_6.setText("");
						textField_7.setText("");
					}
				}
			}
		});

		comboBox.setBounds(129, 10, 438, 21);
		panel_1.add(comboBox);

		JLabel lblNewLabel_7 = new JLabel("Produits en stock");
		lblNewLabel_7.setBounds(10, 14, 118, 13);
		panel_1.add(lblNewLabel_7);

		JButton btnNewButton_1 = new JButton("Ajouter");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table_1.getModel();
				if (comboBox.getSelectedIndex() != 0) {

					ProduitDTO pdto = (ProduitDTO) comboBox.getSelectedItem();

					// Vérifier si le produit existe déjà dans la commande
					boolean produitExiste = false;
					for (int i = 0; i < model.getRowCount(); i++) {
						if (model.getValueAt(i, 0).equals(pdto.getId())) {
							produitExiste = true;
							break;
						}
					}

					if (produitExiste)
						JOptionPane.showMessageDialog(null, "Produit déjà ajouté à votre commande!", "Message", 1);
					else {
						try {
							int qt = Integer.parseInt(textField_5.getText());
							if (qt <= 0) {
								JOptionPane.showMessageDialog(null, "La quantité doit être positive!", "Message", 1);
								return;
							}

							Ligne_CommandeDTO ligne = ligneCommandeController.newLigne(qt, pdto);
							if (ligne != null) {
								Vector<Object> row = new Vector<Object>();
								row.add(ligne.getProduit().getId());
								row.add(ligne.getProduit().getLibelle());
								row.add(ligne.getQuantite());
								row.add(ligne.getProduit().getPrix());
								row.add(ligne.getSous_total());
								model.addRow(row);

								commandeController.ajouterLigne(ligne);

								// Mettre à jour le total
								double total = commandeController.getCommande().getTotal();
								textField_8.setText(String.format("%.2f", total));

								// Réinitialiser la quantité
								textField_5.setText("1");
							} else {
								JOptionPane.showMessageDialog(null, "Quantité insuffisante en stock!", "Attention", 0);
							}
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "La quantité doit être un nombre valide!", "Message", 1);
						}
					}
				} else
					JOptionPane.showMessageDialog(null, "Vous devez sélectionner un produit!", "Message", 1);
			}
		});
		btnNewButton_1.setBounds(579, 64, 85, 21);
		panel_1.add(btnNewButton_1);

		JLabel lblNewLabel_8 = new JLabel("Désignation");
		lblNewLabel_8.setBounds(10, 68, 132, 13);
		panel_1.add(lblNewLabel_8);

		textField_6 = new JTextField();
		textField_6.setFont(new Font("Dialog", Font.BOLD, 14));
		textField_6.setEnabled(false);
		textField_6.setBounds(83, 65, 210, 19);
		panel_1.add(textField_6);
		textField_6.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Prix");
		lblNewLabel_9.setBounds(303, 68, 45, 13);
		panel_1.add(lblNewLabel_9);

		textField_7 = new JTextField();
		textField_7.setFont(new Font("Dialog", Font.BOLD, 14));
		textField_7.setEnabled(false);
		textField_7.setBounds(338, 65, 96, 19);
		panel_1.add(textField_7);
		textField_7.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(40, 409, 670, 200);
		contentPane.add(scrollPane_1);

		table_1 = new JTable();

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		Vector<String> titles = new Vector<String>();
		List<String> liste = new ArrayList<>();
		liste.add("Référence");
		liste.add("Désignation");
		liste.add("QTE");
		liste.add("Prix");
		liste.add("Sous Total");
		titles.addAll(liste);

		table_1.setModel(new DefaultTableModel(data, titles) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Rendre toutes les cellules non éditables
			}
		});
		scrollPane_1.setViewportView(table_1);

		JLabel lblNewLabel_10 = new JLabel("Total Commande");
		lblNewLabel_10.setBounds(507, 619, 95, 13);
		contentPane.add(lblNewLabel_10);

		textField_8 = new JTextField();
		textField_8.setForeground(new Color(0, 0, 255));
		textField_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField_8.setEditable(false);
		textField_8.setBounds(617, 619, 96, 19);
		contentPane.add(textField_8);
		textField_8.setColumns(10);

		JButton btnNewButton_2 = new JButton("Enregistrer");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formattedTextField.getText().equals("____-__-__") || textField_2.getText().equals("")
						|| table_1.getModel().getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Merci de compléter les données de la commande !", "Message", 1);
				} else {
					try {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
						Date date = formatter.parse(formattedTextField.getText());
						commandeController.getCommande().setDatecmd(date);

						commandeController.saveCommande(commandeController.getCommande());
						JOptionPane.showMessageDialog(null, "Commande enregistrée avec succès!", "Succès", 1);

						produitController.decrease_stock();
						remplircombo();
						commandeController.initialiserCommande();

						// Réinitialiser l'interface
						formattedTextField.setValue(null);
						textField_1.setText("");
						textField_2.setText("");
						textField_3.setText("");
						textField_4.setText("");
						textField_5.setText("1");
						textField_6.setText("");
						textField_7.setText("");
						textField_8.setText("");
						comboBox.setSelectedIndex(0);
						DefaultTableModel model = (DefaultTableModel) table_1.getModel();
						model.setRowCount(0);

						// Créer une nouvelle commande
						commandeController.createCommande();

					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(null, "Format de date invalide! Utilisez AAAA-MM-JJ", "Erreur", 0);
						e1.printStackTrace();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement: " + ex.getMessage(), "Erreur", 0);
					}
				}
			}
		});
		btnNewButton_2.setBounds(605, 656, 105, 21);
		contentPane.add(btnNewButton_2);

		// Ajout d'un bouton pour supprimer une ligne (optionnel)


		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("####-##-##");
			mask.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}

		formattedTextField = new JFormattedTextField(mask);
		formattedTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
				try {
					Date date = formatter.parse(formattedTextField.getText());
					commandeController.getCommande().setDatecmd(date);
					System.out.println("Date définie: " + date);
				} catch (ParseException e1) {
					// Ignorer, la validation se fera à l'enregistrement
				}
			}
		});
		formattedTextField.setFont(new Font("Tahoma", Font.BOLD, 10));
		formattedTextField.setBounds(143, 59, 270, 29);
		contentPane.add(formattedTextField);
	}
}