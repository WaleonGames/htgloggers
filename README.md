# HTGLoggers Plugin

HTGLoggers is a logging API designed to simplify the process of logging important information in Minecraft plugins. It allows you to log messages both to the console and to dedicated log files, providing an easy-to-use interface for plugin developers.

## Features

- Create log files for your plugins.
- Log messages to both console and files.
- Simple and clean API for managing logs.
- Supports multiple plugins with separate log files.

## Getting Started

To start using the HTGLoggers API in your Minecraft plugin, follow these steps:

### Prerequisites

- **Minecraft Server**: Ensure you have a Spigot or Paper server running Minecraft version 1.20 or higher.
- **HTGLoggers Plugin**: Download and install the HTGLoggers plugin on your server. You can find the latest version on the [Releases Page](https://github.com/WaleonGames/htgloggers/releases).
  
### Installation

1. Add the JitPack repository to your `pom.xml`:

    ```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    ```

2. Add the HTGLoggers dependency:

    ```xml
    <dependencies>
        <dependency>
            <groupId>com.github.WaleonGames</groupId>
            <artifactId>htgloggers</artifactId>
            <version>latest-version</version> <!-- Replace with the latest version -->
            <scope>provided</scope>
        </dependency>
    </dependencies>
    ```

3. Download the `HTGLoggers.jar` and place it in your `plugins` folder on your Minecraft server.

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

- **INFO**: General information logs.
- **WARNING**: Warnings about potential issues.
- **ERROR**: Errors that need immediate attention.

Example:

```java
HTGLoggersAPI.log("This is an info message.");
HTGLoggersAPI.logToFile("This is a warning message!", "MyPlugin");
```
### Error Handling

Ensure that the HTGLoggers plugin is installed and active before using the API methods. If the plugin is not found or is incompatible, fallback error messages should be logged directly to the console. Always check the plugin instance type to avoid runtime errors.

---

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit them (`git commit -m "Add new feature"`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a pull request.

For more details, visit our [GitHub repository](https://github.com/WaleonGames/htgloggers).

---

## Support

For support, join our [Discord community](https://discord.gg/htgmc) or open an issue on [GitHub](https://github.com/WaleonGames/htgloggers/issues).

---

## Download

You can download the latest version of the HTGLoggers plugin from the following link:

- [SpigotMC HTGLoggers](https://www.spigotmc.org/resources/htgloggers-public-api.119346/history) `Version: 2.1.2.0-release`
- [Modrith HTGLoggers](https://modrinth.com/plugin/htgloggers-public-api/changelog) `Not Found`
- [Curseforge Legacy HTGLoggers](https://legacy.curseforge.com/minecraft/bukkit-plugins/htgloggers-beta-public-api-beta/files)  `Version: 2.1.2.0-release`

Simply select the desired version and download the `HTGLoggers.jar` file to use it in your Minecraft server.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Acknowledgments

- Special thanks to the contributors of this project.
- Inspired by the Minecraft plugin development community.
