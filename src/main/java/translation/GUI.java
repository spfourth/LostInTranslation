package translation;

import javax.swing.*;
import java.awt.event.*;

public class GUI {

    public static void updateTranslations(LanguageCodeConverter lc, CountryCodeConverter cc, Translator t, JLabel jl, JComboBox lcb, JComboBox ccb){
        String language = lc.fromLanguage(lcb.getSelectedItem().toString());
        String country = cc.fromCountry(ccb.getSelectedItem().toString());

        String result = t.translate(country, language);
        if (result == null) {
            result = "no translation found!";
        }
        jl.setText(result);
    }

    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        CountryCodeConverter CCodeConverter = new CountryCodeConverter();
        LanguageCodeConverter LCodeConverter = new LanguageCodeConverter();
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            JComboBox<String> countryComboBox = new JComboBox<>();
            for(String countryCode : translator.getCountryCodes()) {
                countryComboBox.addItem(CCodeConverter.fromCountryCode(countryCode));
            }
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryComboBox);

            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(LCodeConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            languageComboBox.addActionListener(e -> updateTranslations(LCodeConverter,
                               CCodeConverter,
                               translator,
                               resultLabel,
                               languageComboBox,
                               countryComboBox));
            countryComboBox.addActionListener(e -> updateTranslations(LCodeConverter,
                               CCodeConverter,
                               translator,
                               resultLabel,
                               languageComboBox,
                               countryComboBox));

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            updateTranslations(LCodeConverter,
                               CCodeConverter,
                               translator,
                               resultLabel,
                               languageComboBox,
                               countryComboBox);
        });

    }

}
