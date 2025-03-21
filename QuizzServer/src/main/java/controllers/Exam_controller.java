package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Desktop;
import java.util.ResourceBundle;
import components.Answer_card;
import components.Exam_card;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.Answer;
import model.Answer_Select;
import model.Exam;
import model.Group;
import model.HostExam;
import model.Question;
import model.Question_Submiss;
import model.Student;
import model.Student_Test;
import model.Subject;
import model.Submission;
import services.ExamManager;
import services.GroupManager;
import services.HostExamManager;
import services.QuestionManager;
import services.StartServer;
import services.StudentManager;
import services.SubjectManager;
import services.SubmissionManager;
import utils.CheckCheckBox;
import utils.CheckTextField;
import utils.Notification;
import utils.OpenFileExplorer;

public class Exam_controller implements Initializable {
    // FlowPane Main
    @FXML
    private FlowPane flowpane_mainbody;

    @FXML
    private ComboBox<Subject> ComboBox_Subject_AllExam = new ComboBox<Subject>();

    @FXML
    private ComboBox<String> ComboBox_Host_ExamAll = new ComboBox<String>();

    @FXML
    private Label lb_ExamConfirm_ExamManagement;

    // Stack Archive
    @FXML
    private StackPane archive_NewExam;

    @FXML
    private Label lb_ExamName_ExamManagement;

    @FXML
    private Label lb_ExamID_ExamManagement;

    @FXML
    private TextField tf_ExanName_Comfirm_ExamManagement;

    // AnchorPane Create New Exam
    @FXML
    private AnchorPane AnchorPane_newExam_ExamManagement;

    @FXML
    private TextField tf_ExamName_newExam_ExamManagement;

    @FXML
    private ComboBox<Subject> ComboBox_SubejctName_newExam_ExamManagement = new ComboBox<Subject>();

    @FXML
    private TableView<Question> tableView_QuestionBank_ExamManagement = new TableView<Question>();

    @FXML
    private TableColumn<Question, Integer> ID_ColumnQuestion_ExamManagement = new TableColumn<Question, Integer>();

    @FXML
    private TableColumn<Question, String> Question_ColumnQuestion_ExamManagement = new TableColumn<Question, String>();

    @FXML
    private TableView<Question> tableView_ExamQuestion_ExamManagement = new TableView<Question>();

    @FXML
    private TableColumn<Question, Integer> ID_ColumnQuestionExam_ExamManagement = new TableColumn<Question, Integer>();

    @FXML
    private TableColumn<Question, String> Question_ColumnQuestionExam_ExamManagement = new TableColumn<Question, String>();

    @FXML
    private Label lb_totalQuestionBank_ExamManagement = new Label();

    @FXML
    private Label lb_totalExamQuestion_ExamManagement = new Label();

    @FXML
    private TextArea txtArea_Description_ExamManagement = new TextArea();

    // AnchorPane Detail Exam
    @FXML
    private AnchorPane AnchorPane_detailExam_ExamManagement;

    @FXML
    private AnchorPane AnchorPane_viewAllSubmission_detailExam_ExamManagement;

    @FXML
    private AnchorPane AnchorPane_editExam_detailExam_ExamManagement;

    @FXML
    private AnchorPane AnchorPane_hostExam_detailExam_ExamManagement;

    // Stack Pane Question Detail
    @FXML
    private StackPane detailQuestion_ExamManagement;

    @FXML
    private TextArea txtArea_ContentQuestion_detailQuestion_ExamManagement = new TextArea();

    @FXML
    private CheckBox checkbox_MultipleAnswers_detailQuestion_ExamManagement = new CheckBox();

    @FXML
    private Button btn_AddAnswer_detailQuestion_Hidden = new Button();

    @FXML
    private Button btn_SaveChange_ExamManagement = new Button();

    // =============================
    // Table View Question Detail

    @FXML
    private TableView<Question> tableView_QuestionExam_ExamDetail_ExamManagement = new TableView<Question>();

    @FXML
    private TableColumn<Question, Integer> ID_ColumnQuestionExam = new TableColumn<Question, Integer>();

    @FXML
    private TableColumn<Question, String> Question_ColumnQuestionExam = new TableColumn<Question, String>();

    @FXML
    private Label lb_totalQuestion_ExamDetail_ExamManagement = new Label();

    @FXML
    private TextField tf_searchQuestion_ExamManagement = new TextField();

    @FXML
    private TextField tf_ExamName_ExamDetail_ExamManagement = new TextField();

    @FXML
    private TextField tf_SubjectName_ExamDetaiL_ExamManagement = new TextField();

    @FXML
    private TextArea txtArea_Desc_ExamDetail_ExamManagement = new TextArea();

    @FXML
    private VBox VBox_detailQuestion_ExamManagement = new VBox();
    // =============================
    // EXAM EDIT

    @FXML
    private TextField tf_SearchQuestion_EditExam = new TextField();

    @FXML
    private TextField tf_ExamName_EditExam_ExamManagement = new TextField();

    @FXML
    private ComboBox<Subject> ComboBox_Subject_EditExam_ExamMannagement = new ComboBox<Subject>();

    @FXML
    private TextArea txtArea_Desc_EditExam_ExamManagement = new TextArea();

    @FXML
    private TableView<Question> tableView_QuestionBank_EditExam = new TableView<Question>();

    @FXML
    private TableColumn<Question, Integer> ID_Column_QuestionBank_EditExam = new TableColumn<Question, Integer>();

    @FXML
    private TableColumn<Question, String> Question_Column_QuestionBank_EditExam = new TableColumn<Question, String>();

    @FXML
    private TableView<Question> tableView_QuestionExam_EditExam = new TableView<Question>();

    @FXML
    private TableColumn<Question, Integer> ID_Coulmn_QuestionExam_EditExam = new TableColumn<Question, Integer>();

    @FXML
    private TableColumn<Question, String> Question_Coulmn_QuestonExam_EditExam = new TableColumn<Question, String>();

    @FXML
    private Label lb_totalQuestionBank_EditExam = new Label();

    @FXML
    private Label lb_totalQuestionExam_EditExam = new Label();

    // ===== Submission Detail ========================
    @FXML
    private AnchorPane Anchor_submissionDetail_detailExam_ExamManagement;

    // ===== Host Exam ========================

    @FXML
    private TextField tf_IPAddress_HostExam = new TextField();

    @FXML
    private ComboBox<Group> ComboBox_Group_HostExam = new ComboBox<Group>();

    @FXML
    private Label lb_ExamName_SubjectName_HostExam = new Label();

    @FXML
    private TextField tf_TimeLimit_HostExam = new TextField();

    @FXML
    private TextField tf_MaxCore_HostExam = new TextField();

    @FXML
    private CheckBox checkBox_Shuffle_HostExam = new CheckBox();

    @FXML
    private TextField tf_Port_HostExam = new TextField();

    @FXML
    private AnchorPane AnchorPane_StartTest_HostExam;

    @FXML
    private AnchorPane AnchorPane_Testing_HostExam;

    // ============ View All Submission ========================
    @FXML
    private Label lb_totalSubmission_All = new Label();

    @FXML
    private TextField tf_SearchSubmiss_All = new TextField();

    @FXML
    private TableView<Submission> tableView_AllSubmiss_All = new TableView<Submission>();

    @FXML
    private TableColumn<Submission, Integer> ID_Submiss_All = new TableColumn<Submission, Integer>();

    @FXML
    private TableColumn<Submission, Integer> Student_Submiss_All = new TableColumn<Submission, Integer>();

    @FXML
    private TableColumn<Submission, Integer> TimeTaken_Submiss_All = new TableColumn<Submission, Integer>();

    @FXML
    private TableColumn<Submission, Float> Score_Submiss_All = new TableColumn<Submission, Float>();

    // ============ View DEtail Submission ========================

    @FXML
    private Label lb_StudentName_SubDetail = new Label();

    @FXML
    private Label lb_StudentID_SubDetail = new Label();

    @FXML
    private Label lb_Score_SubDetail = new Label();

    @FXML
    private Label lb_TimeTaken_SubDetail = new Label();

    @FXML
    private TableView<Question_Submiss> tableView_SubDetail = new TableView<Question_Submiss>();

    @FXML
    private TableColumn<Question_Submiss, Integer> ID_SubDetail = new TableColumn<Question_Submiss, Integer>();

    @FXML
    private TableColumn<Question_Submiss, String> Question_SubDetail = new TableColumn<Question_Submiss, String>();

    @FXML
    private TableColumn<Question_Submiss, String> Chosen_SubDetail = new TableColumn<Question_Submiss, String>();

    @FXML
    private TableColumn<Question_Submiss, String> Correct_SubDetail = new TableColumn<Question_Submiss, String>();

    private Submission selectedSubmission = new Submission();

    // ====== TESTING ===========================

    public ObservableList<Student_Test> observableList_Testing;

    @FXML
    private Label lb_ExamName_SubjectName_HostExam_Started = new Label();

    @FXML
    private TableView<Student_Test> tableView_Testing = new TableView<Student_Test>();

    @FXML
    private TableColumn<Student_Test, String> ID_Testing = new TableColumn<Student_Test, String>();

    @FXML
    private TableColumn<Student_Test, String> Student_Testing = new TableColumn<Student_Test, String>();

    @FXML
    private TableColumn<Student_Test, Integer> TimeTaken_Testing = new TableColumn<Student_Test, Integer>();

    @FXML
    private TableColumn<Student_Test, Float> Score_Testing = new TableColumn<Student_Test, Float>();

    // =============================

    public static List<Question> listQuestions = new ArrayList<Question>();

    StartServer startServer;

    StudentManager studentManager = StudentManager.getInstance();

    SubmissionManager submissionManager = SubmissionManager.getInstance();

    HostExamManager hostExamManager = HostExamManager.getInstance();

    ExamManager examManager = ExamManager.getInstance();

    SubjectManager subjectManager = SubjectManager.getInstance();

    GroupManager groupManager = GroupManager.getInstance();

    QuestionManager quesManager = QuestionManager.getInstance();

    Exam exam_Current_SubjectManagement = new Exam();

    Subject subject_Current_SubjectManagement = new Subject();

    public static Question questionSelected = new Question();

    int index_SubmissionSelected = 0;

    int index_QuestionSelected = 0;

    int indexSelected_AnchorPane_in_FlowPane = 0;

    int groupID_HostExam = 0;

    public static List<Exam> listExams = new ArrayList<Exam>();

    public List<Subject> listSubjects = subjectManager.getAllSubject(Workspace_controller.current_WorkSpaceID);

    public static List<Question> listQuestions_ExamDetail;

    public ObservableList<Question> observableList_Question;

    public ObservableList<Submission> observableList_Submissions;

    AnchorPane UIAnchorCurrent = new AnchorPane();

    void LoadComboBox_newExam() {
        ComboBox_SubejctName_newExam_ExamManagement.getItems().addAll(listSubjects);

        ComboBox_SubejctName_newExam_ExamManagement.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject subject) {
                return subject == null ? "" : subject.getSubjectName();
            }

            @Override
            public Subject fromString(String s) {
                return null;
            }
        });

        subject_Current_SubjectManagement = null;
    }

    @FXML
    void setOnAction_ComboBoxnewExam(ActionEvent event) {
        subject_Current_SubjectManagement = ComboBox_SubejctName_newExam_ExamManagement.getValue();

        if (subject_Current_SubjectManagement != null) {
            listQuestions = quesManager.getAllQuestionsBySubject(subject_Current_SubjectManagement.getSubjectId());

            tableView_QuestionBank_ExamManagement.getItems().clear();

            tableView_QuestionBank_ExamManagement
                    .setItems(loadQuestion_tableViewQuestionBank_QuestionManagement(listQuestions));

            lb_totalQuestionBank_ExamManagement.setText("Total " + listQuestions.size() + " questions");
        }
    }

    @FXML
    void setOnAction_ComboBoxAllExams(ActionEvent event) {
        subject_Current_SubjectManagement = ComboBox_Subject_AllExam.getValue();

        this.flowpane_mainbody.getChildren().clear();

        if (subject_Current_SubjectManagement != null) {
            listExams = examManager.getAllExamsBySubject(subject_Current_SubjectManagement.getSubjectId());

            for (Exam exam : listExams) {
                add_Exam_FlowPane(new Exam_card(exam, subject_Current_SubjectManagement));
            }
        }
    }

    @FXML
    void setOnAction_ComboBox_HostExamAll(ActionEvent event) {
        String hosted = ComboBox_Host_ExamAll.getValue();

        ArrayList<Exam> list = hosted == "Hosted" ? examManager.getAllExams_Hosted()
                : examManager.getAllExams_UnHosted();

        this.flowpane_mainbody.getChildren().clear();

        if (list == null)
            return;

        for (Exam exam : list)
            add_Exam_FlowPane(new Exam_card(exam, subjectManager.getSubject(exam.getSubjectId())));

    }

    public ObservableList<Question> loadQuestion_tableViewQuestionBank_QuestionManagement(List<Question> list) {

        observableList_Question = FXCollections.observableArrayList(list);

        ID_ColumnQuestion_ExamManagement.setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionId"));
        Question_ColumnQuestion_ExamManagement
                .setCellValueFactory(new PropertyValueFactory<Question, String>("content"));

        return observableList_Question;

    }

    public ObservableList<Question> loadQuestion_tableViewQuestionExam_QuestionManagement(List<Question> list) {

        observableList_Question = FXCollections.observableArrayList(list);

        ID_ColumnQuestionExam_ExamManagement
                .setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionId"));
        Question_ColumnQuestionExam_ExamManagement
                .setCellValueFactory(new PropertyValueFactory<Question, String>("content"));

        return observableList_Question;

    }

    public ObservableList<Question> loadQuestion_tableViewQuestionExamDetail_QuestionManagement(List<Question> list) {

        observableList_Question = FXCollections.observableArrayList(list);

        ID_ColumnQuestionExam
                .setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionId"));
        Question_ColumnQuestionExam
                .setCellValueFactory(new PropertyValueFactory<Question, String>("content"));

        return observableList_Question;

    }

    public ObservableList<Question> loadQuestion_tableViewQuestionBank_EditExam(List<Question> list) {

        observableList_Question = FXCollections.observableArrayList(list);
        ID_Column_QuestionBank_EditExam.setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionId"));
        Question_Column_QuestionBank_EditExam
                .setCellValueFactory(new PropertyValueFactory<Question, String>("content"));
        return observableList_Question;
    }

    public ObservableList<Question> loadQuestion_tableViewQuestionExam_EditExam(List<Question> list) {

        observableList_Question = FXCollections.observableArrayList(list);
        ID_Coulmn_QuestionExam_EditExam
                .setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionId"));
        Question_Coulmn_QuestonExam_EditExam
                .setCellValueFactory(new PropertyValueFactory<Question, String>("content"));
        return observableList_Question;
    }

    public ObservableList<Submission> loadQuestion_tableViewALlSubmiss_All(List<Submission> list) {

        observableList_Submissions = FXCollections.observableArrayList(list);

        ID_Submiss_All.setCellValueFactory(new PropertyValueFactory<Submission, Integer>("submissionId"));
        Student_Submiss_All.setCellValueFactory(new PropertyValueFactory<Submission, Integer>("studentId"));
        TimeTaken_Submiss_All.setCellValueFactory(new PropertyValueFactory<Submission, Integer>("timeTaken"));
        Score_Submiss_All.setCellValueFactory(new PropertyValueFactory<Submission, Float>("score"));

        return observableList_Submissions;
    }

    // Func Create New Exam
    @FXML
    void btn_newExam_Hidden_ExamManagement(ActionEvent event) {

        UIAnchorCurrent = this.AnchorPane_newExam_ExamManagement;

        UIAnchorCurrent.setVisible(true);

        lb_totalQuestionBank_ExamManagement.setText("Total 0 questions");

        lb_totalExamQuestion_ExamManagement.setText("Total 0 questions");

        tableView_QuestionBank_ExamManagement.getItems().clear();

        tableView_ExamQuestion_ExamManagement.getItems().clear();

        tableView_ExamQuestion_ExamManagement
                .setItems(loadQuestion_tableViewQuestionExam_QuestionManagement(listQuestions));

        tableView_ExamQuestion_ExamManagement.getItems().clear();

        LoadComboBox_newExam();

    }

    @FXML
    void btn_CreateExam_newExam_ExamManagement(ActionEvent event) {
        String examName = tf_ExamName_newExam_ExamManagement.getText();
        String desc = txtArea_Description_ExamManagement.getText();

        if (examName.length() == 0 || examName == null || examName == "") {
            Notification.Error("Error", "Please enter exam name");
            return;
        }

        if (examName.length() > 191) {
            Notification.Error("Error", "Exam name is too long");
            return;
        }
        if (subject_Current_SubjectManagement == null) {
            Notification.Error("Error", "Please choose subject");
            return;
        }
        if (tableView_ExamQuestion_ExamManagement.getItems().size() == 0) {
            Notification.Error("Error", "Please choose question");
            return;
        }

        List<Integer> listQuestionIds = new ArrayList<Integer>();

        for (Question question : tableView_ExamQuestion_ExamManagement.getItems()) {
            listQuestionIds.add(question.getQuestionId());
        }

        int examID = examManager.getAllExams().size() + 1;

        Exam exam_current = new Exam(examID,
                subject_Current_SubjectManagement.getSubjectId(), examName, desc, listQuestionIds, false);

        boolean isCreateSuccess = examManager.createExam(exam_current);

        if (!isCreateSuccess) {
            Notification.Error("Error", "Create new exam failed");
            return;
        }

        Notification.Infomation("Success", "Create new exam successfully");

        Exam_card exam_card = new Exam_card(exam_current, subject_Current_SubjectManagement);

        add_Exam_FlowPane(exam_card);
    }

    @FXML
    void btn_ReturnExamQuesFromQuesBank(ActionEvent event) {
        ObservableList<Question> questionReturnQuestionBank = tableView_ExamQuestion_ExamManagement
                .getSelectionModel()
                .getSelectedItems();

        if (questionReturnQuestionBank == null) {
            Notification.Error("Error", "Please choose question");
            return;
        }

        tableView_ExamQuestion_ExamManagement
                .getItems().removeAll(questionReturnQuestionBank);

        int total = listQuestions.size() - questionReturnQuestionBank.size();

        lb_totalExamQuestion_ExamManagement.setText("Total " + total + " questions");
    }

    @FXML
    void btn_SelectQuestionBankToExamQues(ActionEvent event) {

        ObservableList<Question> questionSelectInQuestionBank = tableView_QuestionBank_ExamManagement
                .getSelectionModel()
                .getSelectedItems();

        if (questionSelectInQuestionBank == null) {
            Notification.Error("Error", "Please choose question");
            return;
        }

        tableView_ExamQuestion_ExamManagement.getItems().addAll(questionSelectInQuestionBank);
        int total = tableView_ExamQuestion_ExamManagement.getItems().size();

        lb_totalExamQuestion_ExamManagement
                .setText("Total " + total + " questions");

    }

    // Add group for flowpane
    public void add_Exam_FlowPane(Exam_card exam_card) {
        Insets margin = new Insets(12, 12, 0, 0);

        FlowPane.setMargin(exam_card.getExam_Instance(), margin);

        this.flowpane_mainbody.getChildren().add(exam_card.getExam_Instance());

        setAction_Archive(exam_card);
        setAction_Details(exam_card);

    }

    void setAction_Details(Exam_card exam) {
        exam.getDetails_btn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                exam_Current_SubjectManagement = examManager.getExam(exam.getExam().getExamId());

                btn_details_Exam(event);

            }
        });
    }

    void setAction_Archive(Exam_card exam) {
        exam.getArchive_btn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                archive_NewExam.setVisible(true);

                exam_Current_SubjectManagement = examManager.getExam(exam.getExam().getExamId());

                indexSelected_AnchorPane_in_FlowPane = flowpane_mainbody.getChildren()
                        .indexOf(exam.getExam_Instance());

                if (archive_NewExam.isVisible()) {
                    lb_ExamName_ExamManagement.setText("Exam Name : " + exam_Current_SubjectManagement.getName());
                    lb_ExamID_ExamManagement.setText("ID : #"
                            + (exam_Current_SubjectManagement.getExamId() >= 10 ? exam_Current_SubjectManagement
                                    .getExamId() : "0" + exam_Current_SubjectManagement.getExamId()));
                    lb_ExamConfirm_ExamManagement
                            .setText("To confirm, type \"" + exam_Current_SubjectManagement.getName()
                                    + "\" in the box below");

                    tf_ExanName_Comfirm_ExamManagement.clear();

                    tf_ExanName_Comfirm_ExamManagement.setPromptText(exam_Current_SubjectManagement.getName());

                }

            }
        });
    }

    @FXML
    void setOnActionMultiple_Detail(ActionEvent event) {
        CheckCheckBox.checkMultipleCheckBox(VBox_detailQuestion_ExamManagement,
                checkbox_MultipleAnswers_detailQuestion_ExamManagement);
    }

    // Func detail Exam
    void btn_details_Exam(ActionEvent event) {

        setUIAnchorCurrent(AnchorPane_detailExam_ExamManagement);

        String examName = exam_Current_SubjectManagement.getName();

        int subjectId = exam_Current_SubjectManagement.getSubjectId();

        String subjectName = subjectManager.getSubject(subjectId).getSubjectName();

        subject_Current_SubjectManagement = subjectManager.getSubject(subjectId);

        String description = exam_Current_SubjectManagement.getDesc();

        listQuestions_ExamDetail = new ArrayList<Question>();

        for (Integer questionIds : exam_Current_SubjectManagement.getQuestionsIds()) {

            Question question = quesManager.getQuestion(questionIds);

            listQuestions_ExamDetail.add(question);
        }

        int totalQuestion = listQuestions_ExamDetail.size();

        tf_ExamName_ExamDetail_ExamManagement.setText(examName);

        tf_SubjectName_ExamDetaiL_ExamManagement.setText(subjectName);

        txtArea_Desc_ExamDetail_ExamManagement.setText(description);

        lb_totalQuestion_ExamDetail_ExamManagement.setText(" Total " + totalQuestion
                + " questions");

        if (listQuestions_ExamDetail != null) {
            tableView_QuestionExam_ExamDetail_ExamManagement
                    .setItems(loadQuestion_tableViewQuestionExamDetail_QuestionManagement(listQuestions_ExamDetail));
        }

    }

    // Func Archive Exam
    @FXML
    void btn_ArchiveExam_ExamManagement(ActionEvent event) {
        String examName = tf_ExanName_Comfirm_ExamManagement.getText();

        if (examName.length() == 0 || examName == null || examName == "") {
            Notification.Error("Error", "Please enter exam name");
            return;
        }
        if (!examName.equalsIgnoreCase(lb_ExamName_ExamManagement.getText())) {
            Notification.Error("Error", "Exam name does not match");
            return;
        }

        boolean isAchiveSuccess = examManager.deleteExam(exam_Current_SubjectManagement.getExamId());

        if (!isAchiveSuccess) {
            Notification.Error("Error", "Archive exam failed");
            return;
        }

        // Func Archive Exam
        Notification.Infomation("Success", "Archive exam successfully");

        this.flowpane_mainbody.getChildren().remove(indexSelected_AnchorPane_in_FlowPane);

        listExams.remove(exam_Current_SubjectManagement);

        btn_cancel_ExamManagement(event);

    }

    @FXML
    void btn_cancel_ExamManagement(ActionEvent event) {
        this.archive_NewExam.setVisible(false);
    }

    // ===============================================//
    // Exam Detail FUNCTION
    @FXML
    void btn_detailQuestion_detailExam_ExamManagement(ActionEvent event) {

        questionSelected = tableView_QuestionExam_ExamDetail_ExamManagement
                .getSelectionModel().getSelectedItem();

        index_QuestionSelected = tableView_QuestionExam_ExamDetail_ExamManagement
                .getSelectionModel().getSelectedIndex();

        if (questionSelected == null) {
            Notification.Error("Error", "Please choose question");
            return;
        }

        detailQuestion_ExamManagement.setVisible(true);

        LoadDataOnQuestionDetail(questionSelected, true);

    }

    void LoadDataOnQuestionDetail(Question question, boolean isEdit) {

        // false == edit
        // true == detail

        this.VBox_detailQuestion_ExamManagement.getChildren().clear();

        btn_SaveChange_ExamManagement.setDisable(isEdit);

        btn_AddAnswer_detailQuestion_Hidden.setVisible(!isEdit);

        checkbox_MultipleAnswers_detailQuestion_ExamManagement.setVisible(!isEdit);

        txtArea_ContentQuestion_detailQuestion_ExamManagement.setDisable(isEdit);

        txtArea_ContentQuestion_detailQuestion_ExamManagement.setText(question.getContent());

        for (Answer answer : question.getAnswers()) {

            Answer_card answer_card = new Answer_card(answer);

            setEventButtonDelete(answer_card);

            setEventCheckbox(answer_card, VBox_detailQuestion_ExamManagement);

            answer_card.getCheckBox().setDisable(isEdit);

            answer_card.getButton().setDisable(isEdit);

            answer_card.getTextField().setDisable(isEdit);

            VBox_detailQuestion_ExamManagement.getChildren().add(answer_card.getHBox());
        }

    }

    void LoadDataOnQuestionDetail_SubDetail(Question_Submiss question, boolean isEdit) {

        // false == edit
        // true == detail

        this.VBox_detailQuestion_ExamManagement.getChildren().clear();

        btn_SaveChange_ExamManagement.setDisable(isEdit);

        btn_AddAnswer_detailQuestion_Hidden.setVisible(!isEdit);

        checkbox_MultipleAnswers_detailQuestion_ExamManagement.setVisible(!isEdit);

        txtArea_ContentQuestion_detailQuestion_ExamManagement.setDisable(isEdit);

        txtArea_ContentQuestion_detailQuestion_ExamManagement.setText(question.getContent());

        for (Answer answer : question.getAnswers()) {

            Answer_Select answer_select = new Answer_Select(answer, question.getAnswerSelectedMap()
                    .get(question.getQuestionId()).contains(answer.getAnswerId()));

            Answer_card answer_card = new Answer_card(answer_select);

            setEventButtonDelete(answer_card);

            setEventCheckbox(answer_card, VBox_detailQuestion_ExamManagement);

            answer_card.getCheckBox().setDisable(isEdit);

            answer_card.getButton().setDisable(isEdit);

            answer_card.getTextField().setDisable(isEdit);

            VBox_detailQuestion_ExamManagement.getChildren().add(answer_card.getHBox());
        }

    }

    void setEventButtonDelete(Answer_card answer_card) {
        answer_card.getButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox_detailQuestion_ExamManagement.getChildren().remove(answer_card.getHBox());
            }
        });
    }

    void setEventCheckbox(Answer_card answer_card, VBox vbox) {
        answer_card.getCheckBox().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isMultiple = checkbox_MultipleAnswers_detailQuestion_ExamManagement.isSelected();

                boolean isCheckBox = CheckCheckBox.checkTextBox(vbox);

                if (!isMultiple && !isCheckBox) {

                    answer_card.getCheckBox().setSelected(false);

                    Notification.Error("Error", "Only one answer is allowed");

                    return;
                }
            }
        });
    }

    @FXML
    void btn_duplicateExam_detailExam_ExamManagement(ActionEvent event) {

        // System.out.println("Duplicate");

        boolean isDuplicate = examManager.createExam(exam_Current_SubjectManagement);

        if (!isDuplicate) {
            Notification.Error("Error", "Duplicate exam failed");
            return;
        }
        Notification.Infomation("Success", "Duplicate exam successfully");

        Exam_card exam_card = new Exam_card(exam_Current_SubjectManagement, subject_Current_SubjectManagement);

        add_Exam_FlowPane(exam_card);

    }

    // Edit Exam

    void LoadComboBox_EditExam() {
        ComboBox_Subject_EditExam_ExamMannagement.getItems().addAll(listSubjects);

        ComboBox_Subject_EditExam_ExamMannagement.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject subject) {
                return subject == null ? "" : subject.getSubjectName();
            }

            @Override
            public Subject fromString(String s) {
                return null;
            }
        });

    }

    @FXML
    void btn_editExam_detailExam_Hidden_ExamManagement(ActionEvent event) {

        setUIAnchorCurrent(this.AnchorPane_editExam_detailExam_ExamManagement);

        ComboBox_Subject_EditExam_ExamMannagement.getItems().clear();

        LoadComboBox_AllExams(listSubjects);

        String examName = exam_Current_SubjectManagement.getName();

        int subjectId = exam_Current_SubjectManagement.getSubjectId();

        String description = exam_Current_SubjectManagement.getDesc();

        tf_ExamName_EditExam_ExamManagement.setText(examName);

        txtArea_Desc_EditExam_ExamManagement.setText(description);

        ComboBox_Subject_EditExam_ExamMannagement.getItems().clear();

        LoadComboBox_EditExam();

        ComboBox_Subject_EditExam_ExamMannagement
                .setValue(subjectManager.getSubject(subjectId));

        listQuestions_ExamDetail = new ArrayList<Question>();

        for (Integer questionIds : exam_Current_SubjectManagement.getQuestionsIds()) {

            Question question = quesManager.getQuestion(questionIds);

            listQuestions_ExamDetail.add(question);
        }
        ArrayList<Question> listQuestionsOfSubject = quesManager
                .getAllQuestionsBySubject(subject_Current_SubjectManagement.getSubjectId());
        int totalQuestionBank = listQuestionsOfSubject.size();

        lb_totalQuestionBank_EditExam.setText("Total " + totalQuestionBank + " questions");

        int totalQuestion_ExamEdit = listQuestions_ExamDetail.size();

        lb_totalQuestionExam_EditExam.setText("Total " + totalQuestion_ExamEdit + " questions");

        if (listQuestions_ExamDetail != null) {
            tableView_QuestionBank_EditExam
                    .setItems(loadQuestion_tableViewQuestionBank_EditExam(listQuestionsOfSubject));

            tableView_QuestionExam_EditExam
                    .setItems(loadQuestion_tableViewQuestionExamEdit_QuestionManagement(listQuestions_ExamDetail));
        }

    }

    @FXML
    void setOnAction_ComboBoxSubject_EditExam(ActionEvent event) {
        subject_Current_SubjectManagement = ComboBox_Subject_EditExam_ExamMannagement.getValue();

        listQuestions_ExamDetail = quesManager
                .getAllQuestionsBySubject(subject_Current_SubjectManagement.getSubjectId());

        tableView_QuestionBank_EditExam.getItems().clear();

        tableView_QuestionBank_EditExam
                .setItems(loadQuestion_tableViewQuestionBank_EditExam(listQuestions_ExamDetail));

        lb_totalQuestionBank_EditExam.setText("Total " + listQuestions_ExamDetail.size() + " questions");
    }

    @FXML
    void btn_QuestionDetail_Hidden_EditExam(ActionEvent event) {

        ObservableList<Question> questionSelectedList;

        questionSelectedList = tableView_QuestionBank_EditExam.getSelectionModel().getSelectedItems();

        if (questionSelectedList == null || questionSelectedList.size() == 0) {
            Notification.Error("Error", "Please choose question");
            return;
        }
        if (questionSelectedList.size() > 1) {
            Notification.Error("Error", "Please choose only one question");
            return;
        }

        detailQuestion_ExamManagement.setVisible(true);

        questionSelected = questionSelectedList.get(0);

        index_QuestionSelected = tableView_QuestionBank_EditExam
                .getSelectionModel().getSelectedIndex();

        LoadDataOnQuestionDetail(questionSelected, false);

    }

    @FXML
    void btn_SelectQuestionBank_EditExam(ActionEvent event) {
        ObservableList<Question> selectedItems = tableView_QuestionBank_EditExam
                .getSelectionModel().getSelectedItems();

        if (selectedItems == null) {
            Notification.Error("Error", "Please choose question");
            return;
        }

        tableView_QuestionExam_EditExam.getItems().addAll(selectedItems);

        // tableView_QuestionExam_EditExam
        // .setItems(loadQuestion_tableViewQuestionExamEdit_QuestionManagement(selectedItems));

        int total = tableView_QuestionExam_EditExam.getItems().size();

        lb_totalQuestionExam_EditExam.setText("Total " + total + " questions");
    }

    public ObservableList<Question> loadQuestion_tableViewQuestionExamEdit_QuestionManagement(List<Question> list) {

        ObservableList<Question> observableList = FXCollections.observableArrayList(list);

        ID_Coulmn_QuestionExam_EditExam.setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionId"));
        Question_Coulmn_QuestonExam_EditExam
                .setCellValueFactory(new PropertyValueFactory<Question, String>("content"));
        return observableList;
    }

    @FXML
    void btn_ReturnExamQuesFromQuesBank_EditExam(ActionEvent event) {
        ObservableList<Question> selectedItems = tableView_QuestionExam_EditExam
                .getSelectionModel().getSelectedItems();

        if (selectedItems == null) {
            Notification.Error("Error", "Please choose question in Question Bank");
            return;
        }

        tableView_QuestionExam_EditExam.getItems().removeAll(selectedItems);

        int total = tableView_QuestionExam_EditExam.getItems().size();

        lb_totalQuestionExam_EditExam.setText("Total" + total + " questions");
    }

    @FXML
    void btn_SaveChangeExam_EditExam_ExamManagement(ActionEvent event) {

        String examName = tf_ExamName_EditExam_ExamManagement.getText();

        if (examName.length() == 0 || examName == null || examName == "") {
            Notification.Error("Error", "Please enter exam name");
            return;
        }
        if (examName.length() > 191) {
            Notification.Error("Error", "Exam name is too long");
            return;
        }

        int subjectID = ComboBox_Subject_EditExam_ExamMannagement.getValue().getSubjectId();

        String description = txtArea_Desc_EditExam_ExamManagement.getText();

        if (description.length() > 191) {
            Notification.Error("Error", "Description is too long");
            return;
        }

        ArrayList<Integer> listQuestionIds = new ArrayList<Integer>();

        for (Question question : tableView_QuestionExam_EditExam.getItems()) {
            listQuestionIds.add(question.getQuestionId());
        }

        // Func Update Exam
        Exam exam_Update = new Exam(exam_Current_SubjectManagement.getExamId(), subjectID, examName, description,
                listQuestionIds, false);

        boolean isUpdateSuccess = examManager.updateExam(exam_Update);

        subject_Current_SubjectManagement = ComboBox_Subject_EditExam_ExamMannagement.getValue();

        if (!isUpdateSuccess) {
            Notification.Error("Error", "Update exam failed");
            return;
        }

        Notification.Infomation("Success", "Update exam successfully");

        Exam_card exam_card = new Exam_card(exam_Update, subject_Current_SubjectManagement);

        Insets margin = new Insets(12, 12, 0, 0);

        FlowPane.setMargin(exam_card.getExam_Instance(), margin);

        this.flowpane_mainbody.getChildren().set(indexSelected_AnchorPane_in_FlowPane,
                exam_card.getExam_Instance());

        setAction_Archive(exam_card);
        setAction_Details(exam_card);

        exam_Current_SubjectManagement = examManager.getExam(exam_Update.getExamId());
    }

    @FXML
    void btn_SearchQuestion_EditExam(ActionEvent event) {

        String keyWord = tf_SearchQuestion_EditExam.getText().trim();

        if (keyWord.length() == 0 || keyWord == null || keyWord == "") {
            Notification.Error("Error", "Please enter keyword");
            return;
        }
        if (keyWord.length() > 191) {
            Notification.Error("Error", "Keyword is too long");
            return;
        }
        ArrayList<Question> resultQuestions = new ArrayList<>();

        listQuestions_ExamDetail = quesManager
                .getAllQuestionsBySubject(subject_Current_SubjectManagement.getSubjectId());
        // Func Search Question
        for (Question question : listQuestions_ExamDetail) {
            if (question.getContent().contains(keyWord)) {
                resultQuestions.add(question);
            }
        }

        if (resultQuestions.size() == 0) {
            Notification.Error("Error", "Not found question");
            return;
        }

        lb_totalQuestionBank_EditExam.setText(resultQuestions.size() + " questions");

        tableView_QuestionBank_EditExam.getItems().clear();

        tableView_QuestionBank_EditExam
                .setItems(loadQuestion_tableViewQuestionBank_EditExam(resultQuestions));
    }

    // Host Exam
    @FXML
    void btn_hostExam_detailExam_ExamManagement(ActionEvent event) {

        setUIAnchorCurrent(this.AnchorPane_hostExam_detailExam_ExamManagement);

        LoadDataHostExam();
    }

    void LoadDataHostExam() {
        List<Group> listGroups = groupManager.getAllGroupInWorkSpace(Workspace_controller.current_WorkSpaceID);

        CheckTextField.notCharTextField(tf_Port_HostExam);

        CheckTextField.notCharTextField(tf_MaxCore_HostExam);

        CheckTextField.notCharTextField(tf_TimeLimit_HostExam);

        String ipAddress = StartServer.getIPAddress();

        lb_ExamName_SubjectName_HostExam.setText(exam_Current_SubjectManagement.getName() + "\n"
                + subject_Current_SubjectManagement.getSubjectName());

        tf_IPAddress_HostExam.setText(ipAddress);

        setComboBoxHostExam(ComboBox_Group_HostExam, listGroups);

    }

    void setComboBoxHostExam(ComboBox<Group> comboBox_Group_HostExam, List<Group> listGroups) {

        comboBox_Group_HostExam.getItems().clear();

        comboBox_Group_HostExam.getItems().addAll(listGroups);

        comboBox_Group_HostExam.setConverter(new StringConverter<Group>() {
            @Override
            public String toString(Group group) {
                return group == null ? "" : group.getGroupName();
            }

            @Override
            public Group fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    void setOnAction_ComboBoxGroup_HostExam(ActionEvent event) {
        Group group = ComboBox_Group_HostExam.getValue();
        if (group != null) {
            groupID_HostExam = group.getGroupId();
            return;
        }
        groupID_HostExam = 0;
    }

    @FXML
    void btn_StartTest_HostExam(ActionEvent event) {
        String port = tf_Port_HostExam.getText();

        if (port.length() == 0 || port == null || port == "") {
            Notification.Error("Error", "Please enter port");
            return;
        }

        int maxCore = Integer.parseInt(tf_MaxCore_HostExam.getText());

        if (maxCore < 1) {
            Notification.Error("Error", "Please enter max core");
            return;
        }

        int timeLimit = Integer.parseInt(tf_TimeLimit_HostExam.getText());

        if (timeLimit < 1) {
            Notification.Error("Error", "Please enter time limit");
            return;
        }

        int groupId = groupID_HostExam;

        if (groupId == 0) {
            Notification.Error("Error", "Please choose group");
            return;
        }

        boolean isShuffle = checkBox_Shuffle_HostExam.isSelected();

        ArrayList<Question> listQuestions_HostExam = new ArrayList<Question>();

        for (Integer questionIds : exam_Current_SubjectManagement.getQuestionsIds()) {

            Question question = quesManager.getQuestion(questionIds);

            listQuestions_HostExam.add(question);
        }

        int HostExamID = hostExamManager.getAllHostExams().size() + 1;

        HostExam hostExam = new HostExam(HostExamID, exam_Current_SubjectManagement.getExamId(), groupId, timeLimit,
                maxCore,
                isShuffle, listQuestions_HostExam);

        boolean isCreateSuccess = hostExamManager.createHostExam(hostExam);

        if (!isCreateSuccess) {
            Notification.Error("Error", "Start host exam failed");
            return;
        }

        // Start Server
        try {
            startServer = new StartServer(hostExam, Integer.parseInt(port));

            Notification.Infomation("Success", "Start host exam successfully");

            AnchorPane_StartTest_HostExam.setVisible(false);

            AnchorPane_Testing_HostExam.setVisible(true);

            tableView_Testing.getItems().clear();

        } catch (IOException e) {
            Notification.Error("Error", e.getMessage());
        }

    }

    @FXML
    void btn_ReloadData(ActionEvent event) {

        List<String> infoList = startServer.getConnectedClients();

        ArrayList<Student_Test> studentTests = new ArrayList<Student_Test>();

        for (String info : infoList) {

            String[] infoSplit = info.split("-");

            // System.out.println(infoSplit);

            String studentId = infoSplit[0];

            // System.out.println(studentId);

            String fullName = infoSplit[1];

            // System.out.println(fullName);

            int timeTaken = Integer.parseInt(infoSplit[2]);

            // System.out.println(timeTaken);

            float score = Float.parseFloat(infoSplit[3]);

            // System.out.println(score);

            Student_Test studentTest = new Student_Test(studentId, fullName, timeTaken, score);

            studentTests.add(studentTest);
        }

        Platform.runLater(() -> {
            tableView_Testing.setItems(loadStudent_tableViewTesting_HostExam(studentTests));
        });
    }

    ObservableList<Student_Test> loadStudent_tableViewTesting_HostExam(List<Student_Test> list) {

        observableList_Testing = FXCollections.observableArrayList(list);

        ID_Testing.setCellValueFactory(new PropertyValueFactory<Student_Test, String>("studentId"));

        Student_Testing.setCellValueFactory(new PropertyValueFactory<Student_Test, String>("fullName"));

        TimeTaken_Testing.setCellValueFactory(new PropertyValueFactory<Student_Test, Integer>("timeTaken"));

        Score_Testing.setCellValueFactory(new PropertyValueFactory<Student_Test, Float>("score"));

        return observableList_Testing;

    }

    @FXML
    void btn_CLoseTest(ActionEvent event) throws IOException {
        btn_back_hostExam_ExamManagement(event);
    }

    @FXML
    void btn_back_hostExam_ExamManagement(ActionEvent event) throws IOException {

        if (AnchorPane_Testing_HostExam.isVisible()) {

            AnchorPane_Testing_HostExam.setVisible(false);

            AnchorPane_StartTest_HostExam.setVisible(true);

            startServer.shutdownServer();

            Notification.Infomation("Success", "Close host exam successfully");
            return;
        }

        btn_backExamDetail(event);
    }

    // View All Submission
    @FXML
    void btn_viewAllSubmission_detailExam_ExamManagement(ActionEvent event) {

        setUIAnchorCurrent(this.AnchorPane_viewAllSubmission_detailExam_ExamManagement);

        int examID = exam_Current_SubjectManagement.getExamId();

        ArrayList<Submission> listSubmissions = hostExamManager.getListHostExamById(examID);

        tableView_AllSubmiss_All.getItems().clear();

        tableView_AllSubmiss_All.setItems(loadQuestion_tableViewALlSubmiss_All(listSubmissions));

        lb_totalSubmission_All.setText("Total " + listSubmissions.size() + " submissions");

    }

    @FXML
    void btn_back_allSubmission_ExamManagement(ActionEvent event) {
        btn_backExamDetail(event);
    }

    @FXML
    void btn_back_editExam_ExamManagement(ActionEvent event) {

        btn_backExamDetail(event);

        List<Integer> listQuestionIds = exam_Current_SubjectManagement.getQuestionsIds();

        List<Question> listQuestions_ExamDetail = new ArrayList<Question>();

        for (Integer questionId : listQuestionIds) {

            Question question = quesManager.getQuestion(questionId);

            listQuestions_ExamDetail.add(question);
        }

        int totalQuestion = listQuestions_ExamDetail.size();

        lb_totalQuestion_ExamDetail_ExamManagement.setText("Total " + totalQuestion + " questions");

        tf_ExamName_ExamDetail_ExamManagement.setText(exam_Current_SubjectManagement.getName());

        tf_SubjectName_ExamDetaiL_ExamManagement.setText(subject_Current_SubjectManagement.getSubjectName());

        txtArea_Desc_ExamDetail_ExamManagement.setText(exam_Current_SubjectManagement.getDesc());

        AnchorPane_editExam_detailExam_ExamManagement.setVisible(false);

        tableView_QuestionExam_ExamDetail_ExamManagement.getItems().clear();

        tableView_QuestionExam_ExamDetail_ExamManagement
                .setItems(loadQuestion_tableViewQuestionExam_QuestionManagement(listQuestions_ExamDetail));

    }

    void btn_backExamDetail(ActionEvent event) {

        setUIAnchorCurrent(this.AnchorPane_detailExam_ExamManagement);
    }

    void setUIAnchorCurrent(AnchorPane anchorPane) {
        UIAnchorCurrent.setVisible(false);

        UIAnchorCurrent = anchorPane;

        UIAnchorCurrent.setVisible(true);
    }
    // =========== FUNC VIEW ALL SUBMISSION ===========

    @FXML
    void btn_ExportStranscript_All(ActionEvent event) {
        File file_Current = OpenFileExplorer.Save(event);

        if (file_Current == null) {
            Notification.Error("Error", "Please choose file");
            return;
        }
        String fileNameExel = file_Current.getAbsolutePath();

        List<Submission> listSubmissions = tableView_AllSubmiss_All.getItems();

        Boolean is_ExportSuccess = submissionManager.exportSubmissions("Submission", fileNameExel,
                listSubmissions);

        if (!is_ExportSuccess) {
            Notification.Infomation("Error", "Export file failed");
            return;
        }
        Boolean is_Confirm = Notification.Comfrim("Confirm",
                "Do you want to open file?").getResult() == ButtonType.YES;
        if (is_Confirm)
            OpenFileExel_Export(new File(fileNameExel));
    }

    // Open File
    void OpenFileExel_Export(File fileOpen) {
        if (fileOpen == null) {
            Notification.Error("Error",
                    "Please choose file");
            return;
        }
        try {
            Desktop.getDesktop().open(fileOpen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btn_DeleteSubmission_All(ActionEvent event) {

        selectedSubmission = tableView_AllSubmiss_All.getSelectionModel().getSelectedItem();

        index_SubmissionSelected = tableView_AllSubmiss_All.getSelectionModel().getSelectedIndex();

        if (selectedSubmission == null) {
            Notification.Error("Error", "Please choose submission");
            return;
        }

        boolean isDeleteSuccess = submissionManager
                .deleteSubmission(selectedSubmission.getSubmissionId());

        if (!isDeleteSuccess) {
            Notification.Error("Error", "Delete submission failed");
            return;
        }
        tableView_AllSubmiss_All.getItems().remove(index_SubmissionSelected);

        Notification.Infomation("Success", "Delete submission successfully");
    }

    @FXML
    void btn_DeleteSubmissionDetail(ActionEvent event) {

        boolean isDeleteSuccess = submissionManager
                .deleteSubmission(selectedSubmission.getSubmissionId());

        if (!isDeleteSuccess) {
            Notification.Error("Error", "Delete submission failed");
            return;
        }

        Notification.Infomation("Success", "Delete submission successfully");
    }

    @FXML
    void btn_SubmissDetail_All(ActionEvent event) {
        // Doing
        selectedSubmission = tableView_AllSubmiss_All.getSelectionModel().getSelectedItem();

        if (selectedSubmission == null) {
            Notification.Error("Error", "Please choose submission");
            return;
        }

        int id = selectedSubmission.getStudentId();

        // System.out.println(id);

        Student student = studentManager.getStudentByUid(id);

        System.out.println(student.getFirstName() + " " + student.getLastName());

        setUIAnchorCurrent(Anchor_submissionDetail_detailExam_ExamManagement);

        lb_StudentName_SubDetail.setText(student.getFirstName() + " " + student.getLastName());

        lb_StudentID_SubDetail.setText(student.getStudentId());

        lb_Score_SubDetail.setText(String.valueOf(selectedSubmission.getScore()));

        lb_TimeTaken_SubDetail.setText(String.valueOf(selectedSubmission.getTimeTaken()));

        tableView_SubDetail.setItems(loadQuestion_tableView_SubmissDetail(selectedSubmission));

    }

    public Integer getQuestionID_SubmissDetail(Submission submission) {

        int hostExamId = submission.getHostExamId();

        HostExam hostExam = hostExamManager.getHostExamById(hostExamId);

        ArrayList<Question> listQuestions = hostExam.getExamQuestions();

        Integer question_ID = listQuestions.get(0).getQuestionId();

        return question_ID;
    }

    public ObservableList<Question_Submiss> loadQuestion_tableView_SubmissDetail(Submission submission) {

        ArrayList<Question> listQuestions = hostExamManager.getHostExamById(submission.getHostExamId())
                .getExamQuestions();

        Map<Integer, List<Integer>> answerSelectedMap = submission.getAnswerSelectedMap();

        ArrayList<Question_Submiss> listQuestion_Submiss = new ArrayList<>();

        for (Question question : listQuestions) {
            listQuestion_Submiss.add(new Question_Submiss(question, answerSelectedMap, listQuestions));
        }

        ObservableList<Question_Submiss> observableList_Question = FXCollections
                .observableArrayList(listQuestion_Submiss);

        ID_SubDetail.setCellValueFactory(new PropertyValueFactory<Question_Submiss, Integer>("questionId"));

        Question_SubDetail.setCellValueFactory(new PropertyValueFactory<Question_Submiss, String>("content"));

        Chosen_SubDetail.setCellValueFactory(celldata -> getChosenAnswer(celldata.getValue()));

        Correct_SubDetail.setCellValueFactory(
                celldata -> getCorrectAnswer_SubmissDetail(celldata.getValue().getExamQuestions(),
                        celldata.getValue()));

        return observableList_Question;
    }

    public StringProperty getChosenAnswer(Question_Submiss question) {

        Map<Integer, List<Integer>> answerSelectedMap = question.getAnswerSelectedMap();

        StringProperty chosenAnswer = new SimpleStringProperty();

        int id = question.getQuestionId();

        List<Question> listQuestions = question.getExamQuestions();

        Question questionChosen = new Question();

        for (Question questioncurrent : listQuestions) {
            if (questioncurrent.getQuestionId() == id) {
                questionChosen = questioncurrent;
            }
        }
        List<Answer> listAnswers = questionChosen.getAnswers();

        List<Integer> listAnswersChosen = answerSelectedMap.get(id);

        chosenAnswer.set("[ ");

        for (int i = 0; i < listAnswersChosen.size(); i++) {

            if (listAnswersChosen.get(i) != 0) {

                chosenAnswer.set(chosenAnswer.get() + " " + listAnswers.get(i).getContent());
            }
        }

        chosenAnswer.set(chosenAnswer.get() + " ]");

        return chosenAnswer;

    }

    public StringProperty getCorrectAnswer_SubmissDetail(ArrayList<Question> listQuestions, Question_Submiss question) {

        StringProperty correctAnswer = new SimpleStringProperty();

        correctAnswer.set("[ ");

        for (int i = 0; i < listQuestions.size(); i++) {

            if (listQuestions.get(i).getQuestionId() == question.getQuestionId()) {

                ArrayList<Answer> listAnswers = listQuestions.get(i).getAnswers();

                for (int j = 0; j < listAnswers.size(); j++) {
                    if (listAnswers.get(j).isCorrect()) {

                        correctAnswer.set(correctAnswer.get() + " " + listAnswers.get(j).getContent());

                    }
                }
            }

        }
        correctAnswer.set(correctAnswer.get() + " ]");

        return correctAnswer;
    }

    @FXML
    void btn_SearchSubmiss_All(ActionEvent event) {

        String keyWord = tf_SearchSubmiss_All.getText();

        if (keyWord.length() == 0 || keyWord == null || keyWord == "") {
            Notification.Error("Error", "Please enter keyword");
            return;
        }
        if (keyWord.length() > 191) {
            Notification.Error("Error", "Keyword is too long");
            return;
        }

        int examId = exam_Current_SubjectManagement.getExamId();

        ArrayList<Submission> listSubmissions = hostExamManager.getListHostExamById(examId);

        ArrayList<Submission> listSubmissions_Filter = new ArrayList<Submission>();

        for (Submission submission : listSubmissions) {
            if (submission.getStudentId() == Integer.parseInt(keyWord))
                listSubmissions_Filter.add(submission);

        }

        if (listSubmissions_Filter.size() == 0) {
            Notification.Error("Error", "Not found submission");
            return;
        }

        tableView_AllSubmiss_All.getItems().clear();

        tableView_AllSubmiss_All.setItems(loadQuestion_tableViewALlSubmiss_All(listSubmissions_Filter));

        lb_totalSubmission_All.setText("Total " + listSubmissions_Filter.size() + " submissions");
    }

    // =========== END VIEW detail SUBMISSION ===========
    @FXML
    void btn_ExportSubmisson(ActionEvent event) {
        File file_Current = OpenFileExplorer.Save(event);

        if (file_Current == null) {
            Notification.Error("Error", "Please choose file");
            return;
        }
        String fileNameExel = file_Current.getAbsolutePath();

        List<Question_Submiss> listSubmissions = tableView_SubDetail.getItems();

        Boolean is_ExportSuccess = submissionManager
                .exportSubmissionsQuestionSubmit("Submission Detail", fileNameExel, listSubmissions);

        if (!is_ExportSuccess) {
            Notification.Infomation("Error", "Export file failed");
            return;
        }
        Boolean is_Confirm = Notification.Comfrim("Confirm",
                "Do you want to open file?").getResult() == ButtonType.YES;
        if (is_Confirm)
            OpenFileExel_Export(new File(fileNameExel));

    }

    @FXML
    void btn_BackToExamManagement(ActionEvent event) {
        setUIAnchorCurrent(AnchorPane_detailExam_ExamManagement);

        lb_StudentName_SubDetail.setText("");

        lb_StudentID_SubDetail.setText("");

        lb_Score_SubDetail.setText("");

        lb_TimeTaken_SubDetail.setText("");
    }

    @FXML
    void btn_questionDetail_SubmissDetail(ActionEvent event) {

        Question_Submiss questionSelected = tableView_SubDetail.getSelectionModel().getSelectedItem();

        if (questionSelected == null) {
            Notification.Error("Error", "Please choose question");
            return;
        }
        detailQuestion_ExamManagement.setVisible(true);

        LoadDataOnQuestionDetail_SubDetail(questionSelected, true);

    }

    @FXML
    void btn_back_SubDetail(ActionEvent event) {

        setUIAnchorCurrent(AnchorPane_viewAllSubmission_detailExam_ExamManagement);

        lb_StudentName_SubDetail.setText("");

        lb_StudentID_SubDetail.setText("");

        lb_Score_SubDetail.setText("");

        lb_TimeTaken_SubDetail.setText("");

        tableView_AllSubmiss_All.getItems().clear();

        int examID = exam_Current_SubjectManagement.getExamId();

        ArrayList<Submission> listSubmissions = hostExamManager.getListHostExamById(examID);

        tableView_AllSubmiss_All.getItems().clear();

        tableView_AllSubmiss_All.setItems(loadQuestion_tableViewALlSubmiss_All(listSubmissions));

        lb_totalSubmission_All.setText("Total " + listSubmissions.size() + " submissions");

    }

    // ===============================================﻿//

    // Func back

    // Back Create New Exam
    @FXML
    void btn_back_newExam_ExamManagement(ActionEvent event) {
        UIAnchorCurrent.setVisible(false);
    }

    // Back Detail Exam
    @FXML
    void btn_back_detailExam_ExamManagement(ActionEvent event) {
        UIAnchorCurrent.setVisible(false);
    }

    @FXML
    void btn_cancelQuesDetail_ExamManagement(ActionEvent event) {
        detailQuestion_ExamManagement.setVisible(false);
    }

    @FXML
    void btn_addAnswer_detailQuestion_ExamManagement(ActionEvent event) {
        Answer_card answer_card = new Answer_card(new Answer());

        setEventButtonDelete(answer_card);

        setEventCheckbox(answer_card, VBox_detailQuestion_ExamManagement);

        VBox_detailQuestion_ExamManagement.getChildren().add(answer_card.getHBox());
    }

    public ArrayList<Answer> getAnswers(VBox vbox) {

        ArrayList<Answer> answers = new ArrayList<>();

        for (Node node : vbox.getChildren()) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                Answer answer = new Answer();

                String content = ((TextField) hBox.getChildren().get(1)).getText();
                boolean isCorrect = ((CheckBox) hBox.getChildren().get(0)).isSelected();

                answer.setContent(content);
                answer.setCorrect(isCorrect);

                answers.add(answer);
            }
        }

        return answers;

    }

    @FXML
    void btn_SaveChange_detailQuestion_ExamManagement(ActionEvent event) {
        String contentQuestion = txtArea_ContentQuestion_detailQuestion_ExamManagement.getText();

        if (contentQuestion.length() == 0 || contentQuestion == null || contentQuestion == "") {
            Notification.Error("Error", "Please enter content question");
            return;
        }
        if (contentQuestion.length() > 1000) {
            Notification.Error("Error", "Content question is too long");
            return;
        }

        ArrayList<Answer> answers = getAnswers(VBox_detailQuestion_ExamManagement);

        if (answers == null) {
            Notification.Error("Error", "Please enter answers");
            return;
        }

        if (answers.size() < 2) {
            Notification.Error("Error", "Please enter at least 2 answers");
            return;
        }

        if (CheckCheckBox.checkChoiceNoOrOne(VBox_detailQuestion_ExamManagement)) {
            Notification.Error("Error", "Please choose correct answer");
            return;
        }

        questionSelected.setContent(contentQuestion);

        questionSelected.setAnswers(getAnswers(VBox_detailQuestion_ExamManagement));

        // questionSelected.setChapter("chapter1");

        // questionSelected.setDifficulty(1);

        boolean isUpdateSuccess = quesManager.updateQuestion(questionSelected);

        if (!isUpdateSuccess) {
            Notification.Error("Error", "Update question failed");
            return;

        }
        tableView_QuestionBank_EditExam.getItems().set(index_QuestionSelected, questionSelected);

        Notification.Infomation("Success", "Update question successfully");

        detailQuestion_ExamManagement.setVisible(false);
    }

    // ===============================================
    // Search Question in Exam Detail
    @FXML
    void btn_SearchQuestion_ExamManagement(ActionEvent event) {
        String keyWord = tf_searchQuestion_ExamManagement.getText();

        if (keyWord.length() == 0 || keyWord == null || keyWord == "") {
            Notification.Error("Error", "Please enter keyword");
            return;
        }

        if (keyWord.length() > 191) {
            Notification.Error("Error", "Keyword is too long");
            return;
        }

        ArrayList<Question> listQuestion_Search = new ArrayList<Question>();

        for (Question question : listQuestions_ExamDetail) {
            if (question.getContent().contains(keyWord)) {
                listQuestion_Search.add(question);
            }
        }

        if (listQuestion_Search.size() == 0) {
            Notification.Error("Error", "Keyword not found");
            return;
        }
        tableView_QuestionExam_ExamDetail_ExamManagement.getItems().clear();

        tableView_QuestionExam_ExamDetail_ExamManagement
                .setItems(loadQuestion_tableViewQuestionExam_QuestionManagement(listQuestion_Search));

    }

    // Need to fix
    public void LoadListExam() {

        ArrayList<Exam> listExam_Current = new ArrayList<Exam>();
        for (Subject subject : listSubjects) {
            listExam_Current = examManager.getAllExamsBySubject(subject.getSubjectId());

            for (Exam exam : listExam_Current) {
                listExams.add(exam);

                Subject subject_current = subjectManager.getSubject(exam.getSubjectId());

                add_Exam_FlowPane(new Exam_card(exam, subject_current));
            }
        }

    }

    public void LoadComboBox_AllExams(List<Subject> listSubjects2) {
        ComboBox_Subject_AllExam.getItems().addAll(listSubjects2);

        ComboBox_Subject_AllExam.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject subject) {
                return subject == null ? "" : subject.getSubjectName();
            }

            @Override
            public Subject fromString(String s) {
                return null;
            }
        });
    }

    public void LoadComboBox_HostExamAll() {
        ComboBox_Host_ExamAll.getItems().addAll("Un-Hosted", "Hosted");
    }

    /* =============================================== */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // LoadListGroup & Set button details & button archive
        LoadListExam();

        LoadComboBox_AllExams(listSubjects);

        LoadComboBox_HostExamAll();

        tableView_QuestionBank_ExamManagement.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView_ExamQuestion_ExamManagement.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView_QuestionBank_EditExam.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView_QuestionExam_EditExam.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
