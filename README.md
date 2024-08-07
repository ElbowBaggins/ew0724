# ToolTime
Something to do with tools and point of sale. It is a mystery.

Requires Java 22. Uses preview features.

Requires Gradle, use the Gradle Wrapper.

The build script has already been modified to include `--enable-preview` where needed.

Written and tested on macOS, procedures for other platforms should be similar.

`gradlew build` Will, naturally, build the thing.

`gradlew run` Will, naturally, run the thing.

`gradlew test` Will, naturally, test the thing.

`gradlew javadoc` Will, naturally, generate Javadocs. These should be fairly complete and render successfully.

Alternatively, open the project in IntelliJ and use the Gradle tool window to point and click your way to victory.

If you are trying this at the command line and `java -version` does NOT indicate Java 22, this probably won't work.
Amend your path such that `java` and friends are the JDK22 versions.
