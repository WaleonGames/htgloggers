---
icon: magnifying-glass-chart
---

# Installation

{% hint style="success" %}
It is recommended to set it to version 2.1.3.0-beta&#x20;
{% endhint %}

### Installation

*   Add the JitPack repository to your `pom.xml`:

    ```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    ```
*   Add the HTGLoggers dependency:

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
* Download the `HTGLoggers.jar` and place it in your `plugins` folder on your Minecraft server.

<figure><img src="https://jitpack.io/v/WaleonGames/htgloggers.svg" alt=""><figcaption></figcaption></figure>
