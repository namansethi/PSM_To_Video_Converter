package sample;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Controller {

    protected final static String LONG_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    @FXML
    private Button loadFileButton;
    @FXML
    private Button parseFileButton;
    @FXML
    private Label loadingLabel;
    @FXML
    private Label minValueLabel;
    @FXML
    private Label maxValueLabel;
    @FXML
    private
    RangeSlider thresholdSlider;
    @FXML
    private ProgressBar loadingBar;
    @FXML
    private Label fileName;
    private XsensorASCIIParser xsensorASCIIParser;
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_LENGTH = 400;
    private File psmFile;
    private File videoFile;

    private int totalNumberOfFrames;
    private PSMRecording psmRecording;
    private float maxPressure = 0.4f;
    private float minPressure = 0.09f;
    private ArrayList<BufferedImage> frameList;
    private Stage primaryStage;
    private IMediaWriter writer;

    @FXML
    public void initialize() {


        loadFileButton.setOnAction(event -> psmFile = openPsmFile(event));

        parseFileButton.setOnAction(event -> convertPSMToVideo());

        setSliderSettings();


    }

    private void setSliderSettings() {

        thresholdSlider.setLowValue(minPressure);
        thresholdSlider.setHighValue(maxPressure);

        minValueLabel.textProperty().bind(thresholdSlider.lowValueProperty().asString());
        maxValueLabel.textProperty().bind(thresholdSlider.highValueProperty().asString());




    }

    private void convertPSMToVideo() {

        maxPressure = Float.parseFloat(maxValueLabel.getText());
        minPressure = Float.parseFloat(minValueLabel.getText());



        if (psmFile != null) {
            xsensorASCIIParser = new XsensorASCIIParser(psmFile);
            if(xsensorASCIIParser.parseForFirstFrameNumber() == 1){
                totalNumberOfFrames = xsensorASCIIParser.parseForLastFrameNumber();

            }else{
                totalNumberOfFrames = xsensorASCIIParser.parseForLastFrameNumber()-xsensorASCIIParser.parseForFirstFrameNumber();
            }
            psmRecording = xsensorASCIIParser.parseHeader();


            frameList = new ArrayList<>();

            xsensorASCIIParser.resetBrAndCurrentFrame();
            xsensorASCIIParser.parseHeader();

            encodeVideo2((Stage) loadFileButton.getScene().getWindow());
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("You have not loaded a file yet");
            alert.setContentText("Please use the load button to select a psmfile to parse.");
            alert.showAndWait();
        }

    }

    private File openPsmFile(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PSM File (.csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
        if (file != null) {
            fileName.setText(file.getName());
        }
        return file;
    }


    private void encodeVideo2(Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Converted Video");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video File (.mp4)", "*.mp4"));
        File outputFile = fileChooser.showSaveDialog(stage);

        if(outputFile != null) {
            String outputFilepath = outputFile.getAbsolutePath();
            writer = ToolFactory.makeWriter(outputFilepath);

            writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, FRAME_WIDTH, FRAME_LENGTH);


            final Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i < totalNumberOfFrames; i++) {
                        xsensorASCIIParser.parseFrame();
                        updateMessage("Encoding Frame: " + psmRecording.getFrameHeader(0, "Frame").stringValue());
                        BufferedImage drawnImage = drawFrame(psmRecording.getFrameData(0));
                        writer.encodeVideo(0, drawnImage, (long) ((Math.pow(10, 9)) / 18) * i, TimeUnit.NANOSECONDS);
                        updateProgress(i + 1, totalNumberOfFrames);
                    }
                    writer.close();
                    updateMessage("Encoding Complete!");

                    return null;
                }
            };

            loadingBar.progressProperty().bind(
                    task.progressProperty()
            );
            loadingLabel.textProperty().bind(task.messageProperty());


            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }
    }
    private BufferedImage drawFrame(float[][] frameData) {
        int length = frameData.length;
        int width = frameData[0].length;

        int imageWidth = FRAME_WIDTH;
        int imageLength = FRAME_LENGTH;
        int squareSize = imageWidth / width;

        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageLength, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        for (int j = 0; j < length; j++) {
            for (int i = 0; i < width; i++) {

                graphics2D.setColor(getColorForPressureValue(frameData[j][i]));
                graphics2D.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
        return bufferedImage;

    }

    private Color getColorForPressureValue(float value) {
        ColorMap colorMap = new ColorMap();
        int colorMapSize = colorMap.getColors().length;
        int colorIndex = Math.round(((value - minPressure) * (colorMapSize - 1)) / (maxPressure - minPressure));
        if (colorIndex < 0) {
            colorIndex = 0;
        } else if (colorIndex > colorMapSize - 1) {
            colorIndex = colorMapSize - 1;
        }

        return colorMap.getColors()[colorIndex];
    }


}
