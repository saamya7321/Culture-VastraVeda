package vastraveda.features.feature7_timeline;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;
import vastraveda.core.models.ClothingItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Feature7UI extends BaseUI implements Feature {

    private final Feature7Service service = new Feature7Service();
    private JPanel cardsPanel;
    private JList<String> eraList;

    // Defined eras for the timeline
    private final String[] eras = {"Ancient", "Medieval", "Mughal", "Colonial", "Modern"};
    private final String[] periods = {"3300 BCE - 500 CE", "500 CE - 1500 CE", "1526 CE - 1857 CE", "1858 CE - 1947 CE", "1947 CE - Present"};

    public Feature7UI() {
        super("Historical Timeline");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("📜 Historical Timeline", "Explore the evolution of Indian clothing through the ages."), BorderLayout.NORTH);

        // --- Left side: Era Selector ---
        eraList = new JList<>(eras);
        eraList.setFont(FONT_BODY);
        eraList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eraList.setFixedCellHeight(50);
        eraList.setBackground(new Color(245, 242, 235));
        eraList.setSelectionBackground(COLOR_PRIMARY);
        eraList.setSelectionForeground(Color.WHITE);

        // Scroll logic: Clicking an era scrolls the right panel
        eraList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                scrollToEra(eraList.getSelectedIndex());
            }
        });

        JScrollPane listScroll = new JScrollPane(eraList);
        listScroll.setPreferredSize(new Dimension(180, 0));

        // --- Right side: Stacked Era Cards ---
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setBackground(COLOR_BG);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (int i = 0; i < eras.length; i++) {
            cardsPanel.add(createEraCard(eras[i], periods[i], i));
            cardsPanel.add(Box.createRigidArea(new Dimension(0, 25))); // Spacing between cards
        }

        JScrollPane cardsScroll = new JScrollPane(cardsPanel);
        cardsScroll.getVerticalScrollBar().setUnitIncrement(16);

        // Split Pane to hold both
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroll, cardsScroll);
        splitPane.setDividerLocation(180);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createEraCard(String eraName, String period, int index) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 180, 140), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(800, 350));

        // Era Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel(eraName + " Era");
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(COLOR_PRIMARY);
        
        JLabel time = new JLabel(period);
        time.setFont(new Font("SansSerif", Font.ITALIC, 14));
        time.setForeground(Color.GRAY);

        header.add(title, BorderLayout.NORTH);
        header.add(time, BorderLayout.SOUTH);
        card.add(header, BorderLayout.NORTH);

        // Matching Items from DataStore
        List<ClothingItem> items = service.getItemsByEra(eraName);
        JPanel itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        itemsPanel.setOpaque(false);

        if (items.isEmpty()) {
            itemsPanel.add(new JLabel("No items found for this era."));
        } else {
            for (ClothingItem item : items) {
                JLabel itemLabel = new JLabel("<html><center>" + item.getImageIcon() + "<br>" + item.getName() + "</center></html>");
                itemLabel.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
                itemLabel.setPreferredSize(new Dimension(100, 80));
                itemsPanel.add(itemLabel);
            }
        }
        
        card.add(itemsPanel, BorderLayout.CENTER);
        return card;
    }

    private void scrollToEra(int index) {
        // Simple logic to scroll to the card position
        Component card = cardsPanel.getComponent(index * 2); // Multiplied by 2 due to RigidArea spacers
        card.getParent().dispatchEvent(new java.awt.event.MouseEvent(card, 0, 0, 0, 0, 0, 0, false));
        Rectangle rect = card.getBounds();
        cardsPanel.scrollRectToVisible(rect);
    }
}