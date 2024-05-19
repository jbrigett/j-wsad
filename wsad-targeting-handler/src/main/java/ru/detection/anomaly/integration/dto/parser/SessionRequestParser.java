package ru.detection.anomaly.integration.dto.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.detection.anomaly.integration.dto.SessionRequestDto;
import ru.detection.anomaly.model.entity.types.ContentType;
import ru.detection.anomaly.model.entity.types.RequestType;
import ru.detection.anomaly.model.entity.types.ResponseCode;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SessionRequestParser {

    //TODO добавиьт session id
    private static String REQUEST_REGEX = "(?<ipOrHost>(?:^|\\b(?<!\\.))(?:1?\\d\\d?|2[0-4]\\d|25[0-5])(?:\\.(?:1?\\d\\d?|2[0-4]\\d|25[0-5])){3}(?=$|[^\\w.])" +
            "|(?:(?:(?:(?:\\w|\\w[\\w\\-])*\\w)\\.)*(?:\\w|\\w[A-Za-z0-9\\-]*\\w)))" +
            "\\s(?<rfcName>.*?)\\s(?<logName>-|[a-z_][a-z0-9_]{0,30})" +
            "\\s(?<dateTime>\\[(?<date>[0-2][0-9]\\/\\w{3}\\/[12]\\d{3}):(?<time>\\d\\d:\\d\\d:\\d\\d).*\\])" +
            "\\s(?<request>\\\"(?<requestMethod>GET|POST|HEAD|PUT|DELETE|CONNECT|OPTIONS|TRACE|PATCH)" +
            "\\s(?<requestUri>\\/[^\\s]*)\\s(?<httpVersion>HTTP/\\d\\.\\d)\\\")" +
            "\\s(?<status>\\d{3})" +
            "\\s(?<byteSent>\\d+)" +
            "\\s\\\"(?<refferrer>[^\\s]+)\\\"" +
            "\\s\\\"(?<userAgent>[^\\\"]+)\\\"" +
            "\\s\\\"(?<forwardFor>[^\\\"]+)\\\"";

    private static String ROBOT_TXT_REGEX = "";

    private static String IMAGE_REGEX = "\"(?<slash>(?:\\/))(?<path>(?:[^\\s\\[\\\",><]*))(?<type>(.jpg|.jpeg|.gif|png))(?<props>(?:\\?[^\\s\\[\\\",><]*)?)\"";

    private static String PDF_FD_REGEX = "";

    private static String HTML_REGEX = "";

    private static final Pattern REQUEST_COMPILER = Pattern.compile(REQUEST_REGEX);
    private static final Pattern ROBOT_TXT_COMPILER = Pattern.compile(ROBOT_TXT_REGEX);
    private static final Pattern IMAGE_COMPILER = Pattern.compile(IMAGE_REGEX);
    private static final Pattern PDF_COMPILER = Pattern.compile(PDF_FD_REGEX);
    private static final Pattern HTML_COMPILER = Pattern.compile(HTML_REGEX);


    public SessionRequestDto parseSessionRequestString(String requestString) {
        Matcher matcher = REQUEST_COMPILER.matcher(requestString);

        if (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));

            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }

        return ;
    }

    private RequestType parseRequestType(String pathToRequest, String refferrer) {
        Matcher matcher = REQUEST_COMPILER.matcher(requestString);

        return ;
    }

    private ContentType parseContentType(String pathToRequest) {
        Matcher matcher = REQUEST_COMPILER.matcher(requestString);


    }

    private ResponseCode parseResponseCode() {
        Matcher matcher = REQUEST_COMPILER.matcher(requestString);

        return ;
    }

    private LocalDateTime parseDateTime() {
        Matcher matcher = REQUEST_COMPILER.matcher(requestString);

        return
    }
}
