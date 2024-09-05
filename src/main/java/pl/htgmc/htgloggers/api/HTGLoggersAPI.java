package pl.htgmc.htgloggers.api;

import java.util.Map;

/**
 * Interfejs API dla pluginu HTGLoggers.
 */
public interface HTGLoggersAPI {

    /**
     * Utwórz plik logu dla określonego pluginu.
     *
     * @param pluginName Nazwa pluginu
     */
    void createLogFile(String pluginName);

    /**
     * Sprawdza, czy logowanie dla danego typu zdarzenia jest włączone.
     *
     * @param eventType Typ zdarzenia
     * @return true, jeśli logowanie jest włączone, false w przeciwnym razie
     */
    boolean isLoggingEnabled(String eventType);

    /**
     * Loguje wiadomość do pliku.
     *
     * @param message Wiadomość do zalogowania
     */
    void log(String message);

    void logToFile(String message);

    String getFormattedMessage(String formatKey, String playerName, String additionalInfo);

    void setFormatProperties(Map<String, String> formatProperties);

    Map<String, String> getFormatProperties();

    String getLogFormat(String formatKey);

    boolean isValidFormat(String formatKey);
}
