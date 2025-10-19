# QuizApp – Embedded 15 Questions per Exam (No Leaderboard)

- Exams: GATE, JEE Mains, JEE Advanced, NEET
- 15 questions per exam embedded in code with options, correct answer, hint, explanation, asked_year
- 50–50 lifeline, hints, per question timer, palette navigation
- Results saved to MySQL (users and results tables). No leaderboard screen.

## 1) Create DB
Open MySQL Workbench and run:
```
sql/quizdb.sql
```

## 2) Configure database credentials
Edit `src/main/resources/db.properties`:
```
db.user=YOUR_USER
db.password=YOUR_PASSWORD
```

## 3) Run
```
mvn clean javafx:run
```
Login with `admin/1234` or sign up.

## Edit questions
Modify `src/main/java/com/quizapp/InMemoryQuestions.java`.
