package vastraveda.features.feature9_gallery;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.models.ClothingItem;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Feature9UI extends BaseUI implements Feature {

    private static final Color COLOR_BG_ALT = null;
    private final Feature9Service service = new Feature9Service();
    private JPanel galleryPanel;
    private JTextField searchField;
    private JComboBox<String> genderDropdown;
    private boolean isGridView = true;

    public Feature9UI() {
        super("Visual Gallery");
        buildUI();
    }

    @Override
    public void render() {
        refreshGallery();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🖼 Visual Gallery", "Browse Indian clothing styles."), BorderLayout.NORTH);

        // --- Toolbar ---
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        toolbar.setBackground(COLOR_BG_ALT);

        searchField = new JTextField(15);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { refreshGallery(); }
            public void removeUpdate(DocumentEvent e) { refreshGallery(); }
            public void changedUpdate(DocumentEvent e) { refreshGallery(); }
        });

        genderDropdown = new JComboBox<>(new String[]{"All", "Male", "Female", "Unisex"});
        genderDropdown.addActionListener(e -> refreshGallery());

        JButton toggleBtn = createStyledButton("Toggle View", COLOR_PRIMARY, Color.WHITE);
        toggleBtn.addActionListener(e -> {
            isGridView = !isGridView;
            refreshGallery();
        });

        toolbar.add(new JLabel("Search:"));
        toolbar.add(searchField);
        toolbar.add(new JLabel("Gender:"));
        toolbar.add(genderDropdown);
        toolbar.add(toggleBtn);

        add(toolbar, BorderLayout.NORTH);

        // --- Gallery Display ---
        galleryPanel = new JPanel();
        galleryPanel.setBackground(COLOR_BG);
        JScrollPane scrollPane = new JScrollPane(galleryPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void refreshGallery() {
        galleryPanel.removeAll();
        List<ClothingItem> items = service.filterItems(searchField.getText(), (String) genderDropdown.getSelectedItem());

        if (isGridView) {
            galleryPanel.setLayout(new GridLayout(0, 3, 15, 15));
        } else {
            galleryPanel.setLayout(new BoxLayout(galleryPanel, BoxLayout.Y_AXIS));
        }

        for (ClothingItem item : items) {
            galleryPanel.add(createItemCard(item));
        }

        galleryPanel.revalidate();
        galleryPanel.repaint();
    }

    private JPanel createItemCard(ClothingItem item) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        if (!isGridView) {
            card.setMaximumSize(new Dimension(800, 100));
        }

        JLabel iconLabel = new JLabel(item.getImageIcon(), JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        card.add(iconLabel, BorderLayout.CENTER);

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        JLabel nameLabel = new JLabel(item.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel regionLabel = new JLabel(item.getRegion(), JLabel.CENTER);
        regionLabel.setForeground(Color.GRAY);
        
        info.add(nameLabel);
        info.add(regionLabel);
        card.add(info, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { card.setBackground(new Color(245, 245, 245)); }
            public void mouseExited(MouseEvent e) { card.setBackground(Color.WHITE); }
            public void mouseClicked(MouseEvent e) { showDetail(item); }
        });

        return card;
    }

    private void showDetail(ClothingItem item) {
        // Removed getFabric() to prevent undefined method errors
        JOptionPane.showMessageDialog(this, 
            "<html><body style='width: 200px;'>" +
            "<h2>" + item.getName() + "</h2>" +
            "<b>Region:</b> " + item.getRegion() + "<br><br>" +
            "<i>" + item.getDescription() + "</i>" +
            "</body></html>", 
            "Item Details", JOptionPane.INFORMATION_MESSAGE);
    }
}