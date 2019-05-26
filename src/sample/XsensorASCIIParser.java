package sample;


import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class XsensorASCIIParser {

    protected final static String LONG_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private File asciiFile;

    private PSMRecording psmRecording;
    private BufferedReader br;
    private int currentFrame = 1;

    public XsensorASCIIParser(File asciiFile) {
        this.asciiFile = asciiFile;
        psmRecording = new PSMRecording();
        currentFrame = parseForFirstFrameNumber();
        try {
            br = new BufferedReader(new FileReader(asciiFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public int parseForLastFrameNumber() {
        ReversedLinesFileReader reversedReader;
        try {
            reversedReader = new ReversedLinesFileReader(asciiFile, Charset.defaultCharset());
            String line;
            line = reversedReader.readLine();
            while (line != null) {
                if (line.startsWith("Frame:")) {
                    String[] frameNumberHeader = line.split(":,");
                    return Integer.parseInt(frameNumberHeader[1]);
                }
                line = reversedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public PSMRecording parseHeader() throws IndexOutOfBoundsException {


        try (BufferedReader ignored = new BufferedReader(new FileReader(asciiFile))) {

            br = new BufferedReader(new FileReader(asciiFile));

            //Process the file header
            String headerLine;
            while ((headerLine = br.readLine()) != null)
            {
                if (headerLine.isEmpty() || headerLine.charAt(0) == ',') {
                    break;
                }
                String[] headerKeyValue = headerLine.split(Pattern.quote(":,"));

                psmRecording.putFileHeader(headerKeyValue[0], headerKeyValue[1]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return psmRecording;
    }



    public PSMRecording parseFrame() throws IOException {




        psmRecording.clearAllFrameData();
        psmRecording.setParsingComplete(false);

        int frameCount = 0;

        String frameLine;


        frameLine = br.readLine();

        if(frameLine!=null) {
            while (!(frameLine.equals("Frame:," + (currentFrame + 1)))) {
                String frameHeaderLine = frameLine;
                while (!frameHeaderLine.contains("Sensels:")) {
                    if (!frameHeaderLine.isEmpty() && !(frameHeaderLine.charAt(0) == ',')) {
                        String[] headerKeyValue = frameHeaderLine.split(":,");
                        String key = headerKeyValue[0];
                        String value = headerKeyValue[1];
                        if (key.equals("Frame")) {
                            psmRecording.addNewFrame();
                            frameCount++;
                        }

                        int frameIndex = frameCount - 1;

                        psmRecording.addFrameHeader(frameIndex, key, value);
                    }
                    frameHeaderLine = br.readLine();
                }

                String senselLine = br.readLine();
                int frameRow = 0;
                while (!senselLine.isEmpty() && senselLine.charAt(0) != ',') {
                    String[] dataValuesStr = senselLine.split(",");
                    float[] dataValues = new float[dataValuesStr.length];
                    for (int i = 0; i < dataValuesStr.length; i++) {
                        dataValues[i] = Float.parseFloat(dataValuesStr[i]);
                    }

                    psmRecording.addFrameData(frameCount - 1, frameRow, dataValues);
                    frameRow++;
                    senselLine = br.readLine();
                    if(senselLine==null){
                        break;
                    }
                }
                br.mark(10000);
                frameLine = br.readLine();
                if(frameLine==null){
                    break;
                }
            }
        }
        br.reset();
        currentFrame++;
        psmRecording.setParsingComplete(true);
        return psmRecording;
    }

    public Date getFrameDate(int i) {

        String stringDate = psmRecording.getFrameHeader(i, "Date").stringValue();
        String stringTime = psmRecording.getFrameHeader(i, "Time").stringValue();
        stringDate = stringDate.replaceAll("^\"|\"$", "");
        stringTime = stringTime.replaceAll("^\"|\"$", "");
        String fullDateString = stringDate.concat(" ").concat(stringTime);
        SimpleDateFormat format = new SimpleDateFormat(LONG_TIME_FORMAT);
        Date fullDate = new Date();
        try {
            fullDate = format.parse(fullDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fullDate;

    }

    public Date getAbsoluteStartDate() {

        String[] date = new String[2];
        String[] time = new String[2];
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(asciiFile));
            String line;
            while (!(line = bufferedReader.readLine()).startsWith("Sensor")) {
                if (line.startsWith("Date")) {
                    date = line.split(":,");

                } else if (line.startsWith("Time")) {

                    time = line.split(":,");

                }


            }

            SimpleDateFormat formatter = new SimpleDateFormat(LONG_TIME_FORMAT);

            time[1] = time[1].replaceAll("\"", "");
            date[1] = date[1].replaceAll("\"", "");

            String stringFinalDate = date[1] + " " + time[1];


            return formatter.parse(stringFinalDate);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetBrAndCurrentFrame() {

        try {
            br = new BufferedReader(new FileReader(asciiFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        currentFrame = parseForFirstFrameNumber();

    }

    public int parseForFirstFrameNumber() {

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(asciiFile));
            String line;
            line = bufferedReader.readLine();
            while (line != null) {
                if (line.startsWith("Frame:")) {
                    String[] frameNumberHeader = line.split(":,");
                    return Integer.parseInt(frameNumberHeader[1]);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
