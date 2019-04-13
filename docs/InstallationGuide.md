# Installation Guide
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.woojiahao/kMD2PDF/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.woojiahao/kMD2PDF/)

The repository is hosted on [Maven Central](https://search.maven.org/artifact/com.github.woojiahao/kMD2PDF). You can 
add it to your project using the following code based on your build tool:

## Maven
```xml
<dependency>
  <groupId>com.github.woojiahao</groupId>
  <artifactId>kMD2PDF</artifactId>
  <version>0.2.1</version>
</dependency>
```

## Gradle
```groovy
implementation 'com.github.woojiahao:kMD2PDF:0.2.2'
```

## Troubleshooting
If there is an issue with locating the dependencies, try the following fix:

1. Navigate to your local Maven repository
2. Go under `com > github > woojiahao` and locate the `kMD2PDF`
3. Delete the `kMD2PDF` folder
4. In your project root, run the following command: `mvn clean install`

If the dependencies for kMD2PDF are loaded but you cannot use any of the files in kMD2PDF like `MarkdownDocument`, apply
the following fix:

1. Below the import, add an import for the dependencies jar:
    
    ```xml
    <dependency>
      <groupId>com.github.woojiahao</groupId>
      <artifactId>kMD2PDF</artifactId>
      <version>0.2.2</version>
      <classifier>jar-with-dependencies</classifier>
    </dependency>
    ```

2. In your project root, run the following command: `mvn clean install`

If you continue to encounter issues, make an issue using the issue system [here.](https://github.com/woojiahao/kMD2PDF/issues)