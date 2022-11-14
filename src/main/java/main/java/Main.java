package main.java;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.xml.soap.Text;

public class Main extends Application {

    Word word_thes;
    Word word_to_find;
    Word word_to_modify;
    Scene search_screen;
    Scene mod_screen;
    Scene thes_screen;
    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        Image speaker_icon = new Image("speaker.png");
        ImageView speakericon = new ImageView(speaker_icon);
        speakericon.setFitWidth(60);
        speakericon.setFitHeight(40);

        Image back_icon = new Image("backicon.jpg");
        ImageView backicon = new ImageView((back_icon));
        backicon.setFitHeight(60);
        backicon.setFitWidth(60);

        /** Scene 1 build (Search screen). */

        //Set up grid
        GridPane scene1_grid = new GridPane();
        scene1_grid.setPadding(new Insets(15,15,15,15));
        scene1_grid.setHgap(8);
        scene1_grid.setVgap(8);


        Button speaker = new Button();
        GridPane.setConstraints(speaker, 35,20);
        speaker.setGraphic(speakericon);
        scene1_grid.getChildren().add(speaker);
        speaker.setOnAction(e -> TextToSpeech.speech("This is a volume test. Don't mind what I'm saying"));

        //Menu Bar set up
        // <============================================>
        //Menu dict = new Menu("Dictionary");
        Button dict = new Button("Dictionary");
        dict.setOnAction(e -> stage.setScene(search_screen));

        //Menu thes = new Menu("Thesaurus");
        Button thes = new Button("Thesaurus");
        thes.setOnAction(e -> stage.setScene(thes_screen));
        //thes.setOnAction(e -> stage.setScene(thes_screen));

        Button trans = new Button("Translate");
        Button study = new Button("Study");
        //Menu trans = new Menu("Translate");
        //Menu study = new Menu("Study");

        HBox menuBar = new HBox(10);
        menuBar.getChildren().addAll(dict, thes, trans, study);
        //MenuBar menuBar = new MenuBar(); //Set menu chá»©c nÄƒng má»Ÿ rá»™ng
        //menuBar.getMenus().addAll(dict, thes, trans, study);

        GridPane.setConstraints(menuBar,0 ,0);
        //GridPane.setConstraints(menuBar,0 ,0);

        // <=============================================>

        //Search result area
        // <=============================================>
        String word_ex = "", word_syn = "", word_ant = "";
        Label word_explain = new Label(word_ex);
        word_explain.setFont(new Font("Arial", 30));

        Label word_synonym = new Label(word_syn);
        word_synonym.setFont(new Font("Arial", 30));

        Label word_antonym = new Label(word_ant);
        word_antonym.setFont(new Font("Arial", 30));

        VBox search_result_area = new VBox(15);
        search_result_area.getChildren().addAll(word_explain, word_synonym, word_antonym);

        GridPane.setConstraints(search_result_area, 25, 20);

        // <=============================================>

        //Search bar and search button
        // <=============================================>
        TextField search_bar1 = new TextField(); //VÃ¹ng nháº­p dá»¯ liá»‡u cáº§n tra
        search_bar1.setPrefSize(300,40);

        Button search_button1 = new Button("Tra tá»«"); //NÃºt tra tá»« (Chuyá»ƒn scene 2 (Result screen))
        search_button1.setPrefSize(70,40);
        search_button1.setOnAction(e -> {
            word_to_find = Dictionary.searchWord(search_bar1.getText());
            if(word_to_find.checkExists()) {
                word_explain.setText(search_bar1.getText() + " ["
                        + word_to_find.getPronounce() + "]" + ": "
                        + word_to_find.getWord_explain()
                );
                word_synonym.setText("Tá»« Ä‘á»“ng nghÄ©a: " + word_to_find.getSynonym());
                word_antonym.setText("Tá»« trÃ¡i nghÄ©a: " + word_to_find.getAntonym());
            }else{
                word_explain.setText("Tá»« nÃ y hiá»‡n Ä‘ang khÃ´ng cÃ³ trong tá»« Ä‘iá»ƒn");
            }
        });

        speaker.setOnAction(k -> TextToSpeech.speech(word_to_find.getWord_target()));

        ChoiceBox <String> search_mode = new ChoiceBox(); //Set mode tra tá»«
        search_mode.getItems().addAll("Anh - Viá»‡t", "Viá»‡t - Anh");
        search_mode.setValue("Anh - Viá»‡t");
        search_mode.setPrefSize(150,40);

        HBox hBox1 = new HBox(10); //Gáº¯n 3 children trÃªn vÃ o 1 HBox (ngang)
        hBox1.getChildren().addAll(search_mode,search_bar1,search_button1);

        GridPane.setConstraints(hBox1,25,8);
        // <=============================================>

        //Additional Function (Compulsory)
        // <=============================================>
        Button add_func = new Button("ÄÃ³ng gÃ³p tá»« má»›i cho tá»« Ä‘iá»ƒn?"); //Chá»©c nÄƒng thÃªm tá»«
        add_func.setPrefSize(400, 70);
        add_func.setOnAction(e -> stage.setScene(mod_screen));

        Button del_func = new Button("Cáº§n xÃ³a nhá»¯ng tá»« khÃ´ng cáº§n thiáº¿t?"); //Chá»©c nÄƒng xÃ³a tá»«
        del_func.setPrefSize(400, 70);
        del_func.setOnAction(e -> stage.setScene(mod_screen));

        Button mod_func = new Button("Chá»‰nh sá»­a cho tá»« Ä‘iá»ƒn chÃ­nh xÃ¡c hÆ¡n?"); //Chá»©c nÄƒng chá»‰nh sá»­a tá»«
        mod_func.setPrefSize(400, 70);
        mod_func.setOnAction(e -> stage.setScene(mod_screen));

        Button dnt_func = new Button("HÃ£y gÃ³p pháº§n lÃ m vÃ­ chÃºng tÃ´i dÃ y hÆ¡n"); //Chá»©c nÄƒng donate cho dev
        dnt_func.setPrefSize(400, 70);

        VBox func_box = new VBox(10); //TÆ°Æ¡ng tá»± nhÆ° VBox nhÆ°ng lÃ  vertical (Dá»c)
        func_box.getChildren().addAll(add_func,del_func,mod_func,dnt_func);

        GridPane.setConstraints(func_box,0, 20);

        // <=============================================>

        /** Scene 2 build (Modifying dictionary). */
        // <=============================================>

        BorderPane mod_border = new BorderPane();
        mod_screen = new Scene(mod_border, 500, 500);

        Label note_label = new Label();
        note_label.setFont(new Font("Arial", 30));

        Button add_true = new Button("CÃ³");
        add_true.setVisible(false);

        Button add_false = new Button("KhÃ´ng");
        add_false.setVisible(false);

        Button del_opt = new Button("XÃ³a tá»«");
        del_opt.setVisible(false);

        Button mod_opt = new Button("Chá»‰nh sá»­a tá»«");
        mod_opt.setVisible(false);

        Button mod_meaning = new Button("Sá»­a nghÄ©a cá»§a tá»«");
        mod_meaning.setVisible(false);

        Button mod_word = new Button("Sá»­a tá»«");
        mod_word.setVisible(false);

        TextField mod_new = new TextField("Äiá»n nghÄ©a/tá»« má»›i vÃ o Ä‘Ã¢y");
        mod_new.setVisible(false);
        mod_new.setPrefSize(400,40);
        mod_new.setAlignment(Pos.CENTER);

        TextField word_mod_target = new TextField("Tá»« cáº§n nháº­p");
        word_mod_target.setVisible(false);

        TextField word_mod_meaning = new TextField("NghÄ©a cáº§n nháº­p");
        word_mod_meaning.setVisible(false);

        Button insert_btn = new Button("ThÃªm tá»«");
        insert_btn.setVisible(false);

        HBox adding_word_hbox = new HBox(10);
        adding_word_hbox.getChildren().addAll(word_mod_target,word_mod_meaning,insert_btn);
        adding_word_hbox.setAlignment(Pos.CENTER);

        VBox note_box = new VBox(15);
        note_box.getChildren().addAll(note_label, del_opt, mod_opt,mod_new,mod_meaning,mod_word,add_true,add_false,adding_word_hbox);

        mod_border.setCenter(note_box);
        note_box.setAlignment(Pos.TOP_CENTER);

        Button back_btn = new Button();
        back_btn.setGraphic(backicon);
        back_btn.setOnAction(e -> stage.setScene(search_screen));

        TextField mod_bar = new TextField("HÃ£y kiá»ƒm tra xem tá»« cÃ³ tá»“n táº¡i trong tá»« Ä‘iá»ƒn");
        mod_bar.setPrefSize(500,40);

        Button mod_btn = new Button("Kiá»ƒm tra");
        mod_btn.setOnAction(e -> {
            word_to_modify = Dictionary.searchWord(mod_bar.getText());
            if(!word_to_modify.checkExists()){
                note_label.setText("Tá»« nÃ y hiá»‡n khÃ´ng tá»“n táº¡i trong tá»« Ä‘iá»ƒn cá»§a chÃºng tÃ´i\n" +
                        "Báº¡n cÃ³ muá»‘n thÃªm tá»« nÃ y vÃ o tá»« Ä‘iá»ƒn?");
                add_false.setVisible(true);
                add_true.setVisible(true);
                del_opt.setVisible(false);
                mod_opt.setVisible(false);
                add_true.setOnAction(l -> {
                    word_mod_meaning.setVisible(true);
                    word_mod_target.setVisible(true);
                    insert_btn.setVisible(true);
                    word_to_modify.setWord_target(word_mod_target.getText());
                    word_to_modify.setWord_explain(word_mod_meaning.getText());
                    insert_btn.setOnAction(p -> Dictionary.insertWord(word_to_modify));
                    word_mod_target.setOnAction(p -> Dictionary.insertWord(word_to_modify));
                    word_mod_meaning.setOnAction(p -> Dictionary.insertWord(word_to_modify));
                });
                add_false.setOnAction(l -> stage.setScene(search_screen));
            }else{
                del_opt.setVisible(true);
                mod_opt.setVisible(true);
                add_false.setVisible(false);
                add_true.setVisible(false);
                note_label.setText("Tá»« nÃ y hiá»‡n Ä‘Ã£ tá»“n táº¡i trong tá»« Ä‘iá»ƒn cá»§a chÃºng tÃ´i\n" +
                        "Liá»‡u báº¡n muá»‘n ...");
                del_opt.setOnAction(k -> Dictionary.deleteWord(word_to_modify.getWord_target()));
                mod_opt.setOnAction(k -> {
                    mod_meaning.setVisible(true);
                    mod_word.setVisible(true);
                    mod_new.setVisible(true);
                    mod_meaning.setOnAction(l -> Dictionary.UpdateWordMeaning(word_to_modify,mod_new.getText()));
                    mod_word.setOnAction(l -> Dictionary.UpdateWord(word_to_modify,mod_new.getText()));
                });
            }
        });

        HBox mod_box = new HBox(10);
        mod_box.getChildren().addAll(back_btn,mod_bar,mod_btn);

        mod_border.setTop(mod_box);
        mod_box.setAlignment(Pos.CENTER);

        // <=============================================>

        /** Thesaurus scene. */
        // <=============================================>
        BorderPane thes_pane = new BorderPane();
        thes_screen = new Scene(thes_pane);

        Label syn_word = new Label();
        syn_word.setFont(new Font("Arial", 30));

        Label ant_word = new Label();
        ant_word.setFont(new Font("Arial", 30));

        VBox thes_box = new VBox(15);
        thes_box.getChildren().addAll(syn_word, ant_word);

        thes_pane.setCenter(thes_box);

        TextField search_thes = new TextField();

        Button thes_button = new Button("Tra tá»«");
        thes_button.setOnAction(e -> {
            word_thes = Dictionary.searchWord(search_thes.getText());
            String syn = "";
            String ant = "";
            if(!word_thes.checkExists()){
                syn_word.setText("Tá»« nÃ y hiá»‡n khÃ´ng cÃ³ trong tá»« Ä‘iá»ƒn");
            }else{
                try {
                    syn = HttpURLConnectionExample.searchSynonym(word_thes.getWord_target());
                    ant = HttpURLConnectionExample.searchAntonym(word_thes.getWord_target());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                syn_word.setText("Tá»« Ä‘á»“ng nghÄ©a: " + syn + "\n");
                ant_word.setText("Tá»« trÃ¡i nghÄ©a: " + ant + "\n");
            }
        });

        thes_pane.setTop(search_thes);
        search_thes.setOnAction(e -> {
            word_thes = Dictionary.searchWord(search_thes.getText());
            String syn = "";
            String ant = "";
            if(!word_thes.checkExists()){
                syn_word.setText("Tá»« nÃ y hiá»‡n khÃ´ng cÃ³ trong tá»« Ä‘iá»ƒn");
            }else{
                try {
                    syn = HttpURLConnectionExample.searchSynonym(word_thes.getWord_target());
                    ant = HttpURLConnectionExample.searchAntonym(word_thes.getWord_target());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                syn_word.setText("Tá»« Ä‘á»“ng nghÄ©a: " + syn + "\n");
                ant_word.setText("Tá»« trÃ¡i nghÄ©a: " + ant + "\n");
            }
        });

        HBox thes_searchfield = new HBox(15);
        thes_searchfield.getChildren().addAll(search_thes, thes_button);
        thes_searchfield.setAlignment(Pos.CENTER);

        thes_pane.setTop(thes_searchfield);

        // <=============================================>


        /** Wrap up and packing. */
        scene1_grid.getChildren().addAll(hBox1,menuBar,func_box, search_result_area);
        search_screen = new Scene(scene1_grid);


        stage.setTitle("Tá»« Ä‘iá»ƒn");
        stage.setScene(search_screen);
        stage.show();
    }


}