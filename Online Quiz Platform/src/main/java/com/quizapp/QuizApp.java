package com.quizapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class QuizApp extends Application {

    private Stage stage;
    private String loggedUser = null;
    private int loggedUserId = -1;

    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;
    private String selectedExam = "";

    private Label questionLabel;
    private ToggleGroup optionsGroup;
    private Label timerLabel;
    private Timeline timer;
    private int defaultTimePerQuestion = 30;
    private int timeLeft = defaultTimePerQuestion;
    private long examStartMillis;
    private ProgressBar progressBar;
    private Label progressText;

    private Map<Integer, String> userAnswers = new HashMap<>();
    private Set<Integer> markedForReview = new HashSet<>();

    private int remainingHints = 3;
    private int remainingFiftyFifty = 2;
    private boolean shuffleQuestions = true;
    private boolean shuffleOptions = true;

    private FlowPane palette;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Quiz Master – Embedded 15 Questions per Exam");
        showLoginScreen();
        stage.show();
    }

    private void showLoginScreen() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(32));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f172a, #1e293b);");

        VBox card = new VBox(14);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(24));
        card.setMaxWidth(460);
        card.setStyle("-fx-background-color: #0b1220; -fx-background-radius: 14; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 18, 0, 0, 8);");

        Label title = new Label("Quiz Master");
        title.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 28));
        title.setTextFill(Color.web("#e2e8f0"));

        Label subtitle = new Label("Login or Sign Up");
        subtitle.setTextFill(Color.web("#93c5fd"));

        Label userLbl = new Label("Username");
        userLbl.setTextFill(Color.web("#cbd5e1"));
        TextField userField = new TextField();
        userField.setPromptText("e.g., student001");
        styleInput(userField);

        Label passLbl = new Label("Password");
        passLbl.setTextFill(Color.web("#cbd5e1"));
        PasswordField passField = new PasswordField();
        passField.setPromptText("Your password");
        styleInput(passField);

        Label message = new Label();
        message.setTextFill(Color.web("#fca5a5"));

        HBox buttonRow = new HBox(10);
        Button loginBtn = solidButton("Login");
        Button signUpBtn = outlineButton("Sign Up");
        buttonRow.getChildren().addAll(loginBtn, signUpBtn);

        loginBtn.setOnAction(e -> {
            String user = userField.getText().trim();
            String pass = passField.getText().trim();
            if (UserDAO.verifyUser(user, pass)) {
                loggedUser = user;
                loggedUserId = UserDAO.getUserId(user);
                showHome();
            } else {
                message.setText("Invalid credentials. Try again.");
            }
        });
        signUpBtn.setOnAction(e -> {
            String user = userField.getText().trim();
            String pass = passField.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                message.setText("Enter username and password to sign up.");
                return;
            }
            if (UserDAO.addUser(user, pass)) {
                message.setTextFill(Color.web("#86efac"));
                message.setText("Account created. Login now.");
            } else {
                message.setTextFill(Color.web("#fca5a5"));
                message.setText("Username exists or DB error.");
            }
        });

        card.getChildren().addAll(title, subtitle, userLbl, userField, passLbl, passField, buttonRow, message);
        VBox container = new VBox(card);
        container.setAlignment(Pos.CENTER);
        root.setCenter(container);

        Scene scene = new Scene(root, 920, 620);
        stage.setScene(scene);
    }

    private void styleInput(TextField tf) {
        tf.setStyle("-fx-background-radius: 8; -fx-padding: 10; -fx-background-color: #111827; -fx-text-fill: white; -fx-border-color: #334155; -fx-border-radius: 8;");
    }
    private Button solidButton(String t) {
        Button b = new Button(t);
        b.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 10 18; -fx-background-radius: 10;");
        b.setFocusTraversable(false);
        return b;
    }
    private Button outlineButton(String t) {
        Button b = new Button(t);
        b.setStyle("-fx-background-color: transparent; -fx-border-color: #2563eb; -fx-text-fill: #bfdbfe; -fx-font-size: 15px; -fx-padding: 9 18; -fx-background-radius: 10; -fx-border-radius: 10;");
        b.setFocusTraversable(false);
        return b;
    }

    private void showHome() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0b1020, #0f172a);");

        VBox center = new VBox(14);
        center.setAlignment(Pos.TOP_CENTER);
        center.setPadding(new Insets(20));

        Label choose = new Label("Choose your exam");
        choose.setTextFill(Color.web("#93c5fd"));
        choose.setFont(Font.font("Poppins", FontWeight.BOLD, 18));

        HBox exams = new HBox(12);
        exams.setAlignment(Pos.CENTER);
        Button bGate = solidButton("GATE");
        Button bMains = solidButton("JEE Mains");
        Button bAdv = solidButton("JEE Advanced");
        Button bNeet = solidButton("NEET");
        exams.getChildren().addAll(bGate,bMains,bAdv,bNeet);

        HBox settings = new HBox(10);
        settings.setAlignment(Pos.CENTER);
        Label timeLbl = new Label("Time per question");
        timeLbl.setTextFill(Color.web("#cbd5e1"));
        ComboBox<Integer> perQ = new ComboBox<>();
        perQ.getItems().addAll(10,15,20,30,45,60);
        perQ.getSelectionModel().select(Integer.valueOf(defaultTimePerQuestion));
        CheckBox shQ = new CheckBox("Shuffle questions");
        shQ.setTextFill(Color.web("#cbd5e1"));
        shQ.setSelected(shuffleQuestions);
        CheckBox shO = new CheckBox("Shuffle options");
        shO.setTextFill(Color.web("#cbd5e1"));
        shO.setSelected(shuffleOptions);
        settings.getChildren().addAll(timeLbl, perQ, shQ, shO);

        HBox bottom = new HBox(10);
        bottom.setAlignment(Pos.CENTER);
        Button logoutBtn = outlineButton("Logout");
        bottom.getChildren().addAll(logoutBtn);

        center.getChildren().addAll(choose, exams, settings, bottom);
        root.setCenter(center);

        bGate.setOnAction(e -> { defaultTimePerQuestion = perQ.getValue(); shuffleQuestions = shQ.isSelected(); shuffleOptions = shO.isSelected(); startExam("GATE"); });
        bMains.setOnAction(e -> { defaultTimePerQuestion = perQ.getValue(); shuffleQuestions = shQ.isSelected(); shuffleOptions = shO.isSelected(); startExam("JEE Mains"); });
        bAdv.setOnAction(e -> { defaultTimePerQuestion = perQ.getValue(); shuffleQuestions = shQ.isSelected(); shuffleOptions = shO.isSelected(); startExam("JEE Advanced"); });
        bNeet.setOnAction(e -> { defaultTimePerQuestion = perQ.getValue(); shuffleQuestions = shQ.isSelected(); shuffleOptions = shO.isSelected(); startExam("NEET"); });

        logoutBtn.setOnAction(e -> showLoginScreen());

        Scene sc = new Scene(root, 1000, 700);
        stage.setScene(sc);
    }

    private void startExam(String exam) {
        selectedExam = exam;
        questions = InMemoryQuestions.getByExam(exam);
        if (questions.size() < 15) {
            Alert a = new Alert(Alert.AlertType.WARNING, exam + " has only " + questions.size() + " questions embedded. Expected 15.");
            a.showAndWait();
            return;
        }
        if (shuffleQuestions) Collections.shuffle(questions);
        if (shuffleOptions) {
            for (Question q : questions) {
                List<String> opts = new ArrayList<>(Arrays.asList(q.options));
                Collections.shuffle(opts);
                q.options = opts.toArray(new String[0]);
            }
        }
        userAnswers.clear();
        markedForReview.clear();
        remainingHints = 3;
        remainingFiftyFifty = 2;
        currentIndex = 0;
        score = 0;
        examStartMillis = System.currentTimeMillis();
        showQuizScreen();
    }

    private void showQuizScreen() {
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(16));
        border.setStyle("-fx-background-color: linear-gradient(to bottom, #0f172a, #111827);");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        Label examTitle = new Label("Exam: " + selectedExam);
        examTitle.setTextFill(Color.web("#f8fafc"));
        examTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 18));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        timerLabel = new Label();
        timerLabel.setTextFill(Color.web("#fde047"));
        timerLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 18));
        header.getChildren().addAll(examTitle, spacer, timerLabel);

        questionLabel = new Label();
        questionLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
        questionLabel.setWrapText(true);
        questionLabel.setTextFill(Color.web("#e2e8f0"));

        optionsGroup = new ToggleGroup();
        VBox optionsBox = new VBox(8);
        optionsBox.setAlignment(Pos.TOP_LEFT);
        optionsBox.setFillWidth(true);

        HBox lifelines = new HBox(10);
        lifelines.setAlignment(Pos.CENTER_LEFT);
        Button hintBtn = outlineButton("Hint (" + remainingHints + ")");
        Button fiftyBtn = outlineButton("50-50 (" + remainingFiftyFifty + ")");
        Label hintText = new Label();
        hintText.setTextFill(Color.web("#e2e8f0"));
        lifelines.getChildren().addAll(hintBtn, fiftyBtn, hintText);

        HBox nav = new HBox(10);
        nav.setAlignment(Pos.CENTER);
        Button prevBtn = solidButton("Prev");
        Button clearBtn = outlineButton("Clear");
        Button markBtn = outlineButton("Mark");
        Button nextBtn = solidButton("Next");
        Button submitBtn = outlineButton("Submit");
        nav.getChildren().addAll(prevBtn, clearBtn, markBtn, nextBtn, submitBtn);

        palette = new FlowPane();
        palette.setHgap(6);
        palette.setVgap(6);
        palette.setPrefWrapLength(150);
        palette.setPadding(new Insets(8));
        palette.setMinWidth(160);
        buildPalette();

        VBox centerBox = new VBox(14, questionLabel, optionsBox, lifelines, nav);
        centerBox.setAlignment(Pos.TOP_LEFT);
        centerBox.setPadding(new Insets(12));
        centerBox.setFillWidth(true);

        HBox progressBox = new HBox(10);
        progressBox.setAlignment(Pos.CENTER_LEFT);
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressText = new Label();
        progressText.setTextFill(Color.web("#e2e8f0"));
        Label progressLbl = new Label("Progress:");
        progressLbl.setTextFill(Color.web("#e2e8f0"));
        progressBox.getChildren().addAll(progressLbl, progressBar, progressText);

        BorderPane.setMargin(palette, new Insets(8,12,8,8));
        border.setTop(header);
        border.setLeft(palette);
        border.setCenter(centerBox);
        border.setBottom(progressBox);

        Scene scene = new Scene(border, 1000, 720);
        stage.setScene(scene);

        scene.setOnKeyPressed(ev -> {
            if (ev.getCode() == KeyCode.N) handleNext();
            if (ev.getCode() == KeyCode.P) {
                saveCurrentAnswer();
                if (currentIndex > 0) currentIndex--;
                showQuestion();
            }
            if (ev.getCode() == KeyCode.M) {
                if (markedForReview.contains(currentIndex)) markedForReview.remove(currentIndex);
                else markedForReview.add(currentIndex);
                updatePaletteButton(currentIndex);
            }
            int digit = keyCodeToIndex(ev.getCode());
            if (digit >= 1) selectOptionByIndex(digit);
        });

        hintBtn.setOnAction(e -> {
            if (remainingHints > 0) {
                String hint = questions.get(currentIndex).hint;
                if (hint != null && !hint.isEmpty()) {
                    remainingHints--;
                    hintText.setText("Hint: " + hint);
                    hintBtn.setText("Hint (" + remainingHints + ")");
                } else {
                    hintText.setText("No hint available.");
                }
            }
        });

        fiftyBtn.setOnAction(e -> {
            if (remainingFiftyFifty <= 0) return;
            Question q = questions.get(currentIndex);
            List<RadioButton> radios = optionsBox.getChildren().stream()
                    .filter(n -> n instanceof RadioButton)
                    .map(n -> (RadioButton) n)
                    .collect(Collectors.toList());
            List<RadioButton> incorrect = radios.stream()
                    .filter(r -> !r.getText().equals(q.correctAnswer))
                    .collect(Collectors.toList());
            Collections.shuffle(incorrect);
            int removed = 0;
            for (RadioButton rb : incorrect) {
                if (removed == 2) break;
                rb.setDisable(true);
                rb.setOpacity(0.35);
                removed++;
            }
            remainingFiftyFifty--;
            fiftyBtn.setText("50-50 (" + remainingFiftyFifty + ")");
        });

        prevBtn.setOnAction(e -> {
            saveCurrentAnswer();
            if (currentIndex > 0) currentIndex--;
            showQuestion();
        });
        clearBtn.setOnAction(e -> { optionsGroup.selectToggle(null); userAnswers.remove(currentIndex); });
        markBtn.setOnAction(e -> {
            if (markedForReview.contains(currentIndex)) { markedForReview.remove(currentIndex); markBtn.setText("Mark"); }
            else { markedForReview.add(currentIndex); markBtn.setText("Marked"); }
        });
        nextBtn.setOnAction(e -> handleNext());
        submitBtn.setOnAction(e -> confirmSubmit());

        showQuestion();
    }

    private int keyCodeToIndex(KeyCode code) {
        switch (code) {
            case DIGIT1: case NUMPAD1: return 1;
            case DIGIT2: case NUMPAD2: return 2;
            case DIGIT3: case NUMPAD3: return 3;
            case DIGIT4: case NUMPAD4: return 4;
            default: return -1;
        }
    }

    private void selectOptionByIndex(int idx) {
        VBox optionsBox = (VBox) ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().get(1);
        if (idx - 1 < optionsBox.getChildren().size()) {
            var n = optionsBox.getChildren().get(idx - 1);
            if (n instanceof RadioButton) ((RadioButton) n).setSelected(true);
        }
    }

    private void buildPalette() {
        palette.getChildren().clear();
        for (int i = 0; i < questions.size(); i++) {
            final int idx = i;
            Button b = new Button(String.valueOf(i + 1));
            b.setMinSize(40, 36);
            b.setMaxSize(60, 40);
            b.setStyle("-fx-background-color: #1f2937; -fx-text-fill: white; -fx-background-radius: 8;");
            b.setOnAction(e -> { saveCurrentAnswer(); currentIndex = idx; showQuestion(); });
            palette.getChildren().add(b);
            updatePaletteButton(i);
        }
    }
    private void updatePaletteButton(int i) {
        if (i < 0 || i >= palette.getChildren().size()) return;
        Button b = (Button) palette.getChildren().get(i);
        boolean answered = userAnswers.containsKey(i) && userAnswers.get(i) != null;
        boolean marked = markedForReview.contains(i);
        String color = "#6b7280"; // grey
        if (answered) color = "#10b981"; // green
        if (marked) color = "#f59e0b"; // orange
        if (i == currentIndex) color = "#3b82f6"; // blue current
        b.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-background-radius: 8;");
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) { showScore(); return; }
        Question q = questions.get(currentIndex);
        questionLabel.setText(String.format("(Year: %d) %d. %s", q.askedYear, currentIndex + 1, q.text));

        VBox optionsBox = (VBox) ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().get(1);
        optionsBox.getChildren().clear();
        if (optionsGroup == null) optionsGroup = new ToggleGroup(); else optionsGroup.getToggles().clear();

        for (String opt : q.options) {
            RadioButton rb = new RadioButton(opt);
            rb.setFont(Font.font("Poppins", 16));
            rb.setTextFill(Color.web("#f1f5f9"));
            rb.setToggleGroup(optionsGroup);
            rb.setMaxWidth(Double.MAX_VALUE);
            rb.setWrapText(true);
            optionsBox.getChildren().add(rb);
        }

        String previously = userAnswers.get(currentIndex);
        if (previously != null) {
            for (Toggle t : optionsGroup.getToggles()) {
                RadioButton rb = (RadioButton) t;
                if (rb.getText().equals(previously)) {
                    optionsGroup.selectToggle(rb);
                    break;
                }
            }
        }

        startTimer();
        updateProgress();
        updateMarkButton();
        for (int i = 0; i < questions.size(); i++) updatePaletteButton(i);
    }

    private void startTimer() {
        if (timer != null) timer.stop();
        timeLeft = defaultTimePerQuestion;
        timerLabel.setText("Time " + timeLeft + " s");
        timer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time " + timeLeft + " s");
            if (timeLeft <= 0) {
                timer.stop();
                handleNext();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void handleNext() {
        saveCurrentAnswer();
        currentIndex++;
        showQuestion();
    }

    private void saveCurrentAnswer() {
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        if (selected != null) {
            userAnswers.put(currentIndex, selected.getText());
        }
    }

    private void updateProgress() {
        long answered = userAnswers.values().stream().filter(Objects::nonNull).count();
        double progress = (double) (currentIndex + 1) / (double) questions.size();
        if (progressBar != null) progressBar.setProgress(progress);
        if (progressText != null) progressText.setText((currentIndex + 1) + "/" + questions.size() + " | Answered: " + answered);
    }

    private void updateMarkButton() {
        HBox nav = (HBox) ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().get(3);
        Button markBtn = (Button) nav.getChildren().get(2);
        markBtn.setText(markedForReview.contains(currentIndex) ? "Marked" : "Mark");
    }

    private void confirmSubmit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Submit Quiz");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to submit");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            computeScore();
            showScore();
        }
    }

    private void computeScore() {
        score = 0;
        for (int i = 0; i < questions.size(); i++) {
            String ans = userAnswers.get(i);
            if (ans == null || ans.isEmpty()) continue;
            if (ans.equals(questions.get(i).correctAnswer)) score += 1;
        }
    }

    private void showScore() {
        if (timer != null) timer.stop();

        int correct = 0, wrong = 0, unanswered = 0;
        for (int i = 0; i < questions.size(); i++) {
            String ans = userAnswers.get(i);
            if (ans == null) unanswered++;
            else if (ans.equals(questions.get(i).correctAnswer)) correct++;
            else wrong++;
        }

        int durationSec = (int)((System.currentTimeMillis() - examStartMillis)/1000);
        if (loggedUserId > 0) {
            ResultsDAO.saveResult(loggedUserId, selectedExam, score, questions.size(), correct, wrong, unanswered, durationSec);
        }

        VBox box = new VBox(16);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(24));
        box.setStyle("-fx-background-color: linear-gradient(to right, #0ea5e9, #2563eb);");

        Label done = new Label("Completed: " + selectedExam);
        done.setFont(Font.font("Poppins", FontWeight.BOLD, 26));
        done.setTextFill(Color.WHITE);

        Label finalScore = new Label("Score: " + score + "/" + questions.size() +
                " | Correct " + correct + " Wrong " + wrong + " Unanswered " + unanswered);
        finalScore.setFont(Font.font("Poppins", 18));
        finalScore.setTextFill(Color.WHITE);

        VBox review = new VBox(10);
        review.setPadding(new Insets(10));
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String userAns = userAnswers.get(i);
            Label yearChip = new Label(String.valueOf(q.askedYear));
            yearChip.setStyle("-fx-background-color:#111827; -fx-text-fill:#f8fafc; -fx-padding:2 8; -fx-background-radius:999;");

            Label qLbl = new Label((i + 1) + ". " + q.text);
            qLbl.setWrapText(true);
            qLbl.setTextFill(Color.WHITE);

            Label your = new Label("Your answer: " + (userAns == null ? "—" : userAns));
            your.setTextFill(userAns != null && userAns.equals(q.correctAnswer) ? Color.LIME : Color.YELLOW);

            Label correctLbl = new Label("Correct: " + q.correctAnswer);
            correctLbl.setTextFill(Color.WHITE);

            Label exp = new Label(q.explanation == null ? "" : ("Explanation: " + q.explanation));
            exp.setWrapText(true);
            exp.setTextFill(Color.WHITE);

            HBox head = new HBox(8, yearChip, qLbl);
            VBox card = new VBox(4, head, your, correctLbl, exp);
            card.setStyle("-fx-background-color: rgba(0,0,0,0.25); -fx-background-radius: 10; -fx-padding: 10;");
            review.getChildren().add(card);
        }
        ScrollPane sc = new ScrollPane(review);
        sc.setFitToWidth(true);

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER);
        Button home = solidButton("Home");
        home.setOnAction(e -> showHome());
        Button retry = outlineButton("Retake");
        retry.setOnAction(e -> startExam(selectedExam));
        Button export = outlineButton("Export CSV");
        export.setOnAction(e -> exportCsv());
        actions.getChildren().addAll(home, retry, export);

        box.getChildren().addAll(done, finalScore, sc, actions);
        VBox.setVgrow(sc, Priority.ALWAYS);
        stage.setScene(new Scene(box, 1000, 720));
    }

    private void exportCsv() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Export Results CSV");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fc.setInitialFileName("results-" + selectedExam.replaceAll("\s+", "_") + ".csv");
            var file = fc.showSaveDialog(stage);
            if (file == null) return;
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println("Question,Your Answer,Correct Answer,Is Correct,Asked Year");
                for (int i = 0; i < questions.size(); i++) {
                    Question q = questions.get(i);
                    String ua = Objects.toString(userAnswers.get(i), "");
                    boolean ok = ua.equals(q.correctAnswer);
                    String line = '"' + q.text.replace('"', '\'') + '"' + ',' + '"' + ua.replace('"', '\'') + '"' + ','
                            + '"' + q.correctAnswer.replace('"', '\'') + '"' + ',' + ok + ',' + q.askedYear;
                    pw.println(line);
                }
            }
            new Alert(Alert.AlertType.INFORMATION, "CSV exported successfully.").showAndWait();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Export failed: " + ex.getMessage()).showAndWait();
        }
    }

    public static void main(String[] args) { launch(); }

    public static class Question {
        int id;
        String text;
        String[] options;
        String correctAnswer;
        String hint;
        String explanation;
        int askedYear;
        public Question(int id, String text, String[] options, String correctAnswer, String hint, String explanation, int askedYear) {
            this.id = id; this.text = text; this.options = options; this.correctAnswer = correctAnswer; this.hint = hint; this.explanation = explanation; this.askedYear = askedYear;
        }
    }
}
