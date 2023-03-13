appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%d %highlight(%-5level) [%thread] %logger{46} - %msg%n'
    }
}

logger('com.smarsh', TRACE)

root(TRACE, ['CONSOLE'])