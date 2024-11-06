# Usage Example

### Usage Example

To create a log file and start saving logs, include the following code in your plugin:

```java
@Override
public void onEnable() {
    Plugin htgLoggersPlugin = getServer().getPluginManager().getPlugin("HTGLoggers");
    if (htgLoggersPlugin instanceof HTGLoggers) {
        HTGLoggersAPI.initialize((HTGLoggers) htgLoggersPlugin);
        HTGLoggersAPI.createLogFile("MyPlugin");
        HTGLoggersAPI.logToFile("Plugin started successfully!", "MyPlugin");
    } else {
        getLogger().log(Level.WARNING, "HTGLoggers plugin not found or incompatible!");
    }
}
```

This will create a log file named `MyPlugin-log.log` in the HTGLoggers data folder and log a startup message.

### Log Levels

You can use different logging levels to categorize your logs:

* **INFO**: General information logs.
* **WARNING**: Warnings about potential issues.
* **ERROR**: Errors that need immediate attention.

Example:

```java
HTGLoggersAPI.log("This is an info message.");
HTGLoggersAPI.logToFile("This is a warning message!", "MyPlugin");
```

{% hint style="danger" %}
### Error Handling

Ensure that the HTGLoggers plugin is installed and active before using the API methods. If the plugin is not found or is incompatible, fallback error messages should be logged directly to the console. Always check the plugin instance type to avoid runtime errors.
{% endhint %}
