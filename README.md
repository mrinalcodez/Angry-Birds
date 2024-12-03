# Angry Birds Clone in libGDX (Java)

This is a simple Angry Birds clone game developed using libGDX, a popular framework for building cross-platform games. The game is designed to run on Windows, but libGDX allows you to extend it to other platforms as well. This project demonstrates basic game mechanics, including projectile physics, level design, and collision detection.

## Features

- **Physics Engine**: Utilizes libGDX's Box2D physics for realistic projectile motion and object interactions.
- **Levels**: Multiple levels with different obstacles and targets.
- **Game Assets**: Custom graphics, sounds, and animations to resemble a bird-flinging experience.
- **Windows Compatibility**: Designed to run on Windows, but can be extended to other platforms using libGDX.

## Prerequisites

Before running the game, ensure you have the following installed:

1. **Java Development Kit (JDK)** - Version 8 or higher. Download from [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html).
2. **Gradle** - The build tool used for managing dependencies and building the game. Download from [Gradle Official Website](https://gradle.org/install/).
3. **libGDX** - Game development framework. You can download it from the [libGDX website](https://libgdx.badlogicgames.com/).
4. **IDE (Integrated Development Environment)** - IntelliJ IDEA, Eclipse, or any Java IDE that supports Gradle.

## Setting Up the Project

### 1. Clone the Repository

Clone this repository to your local machine using Git:

```bash
git clone https://github.com/mrinalcodez/Angry-Birds.git
```
### 2. Import the Project into Your IDE
**IntelliJ IDEA:**
- Open IntelliJ IDEA.
- Select "Open Project" and navigate to the cloned repository folder.
- Select the project folder and open it.

**Eclipse:**
- Open Eclipse.
- Go to File > Import > Gradle > Existing Gradle Project.
Navigate to the project directory and finish the setup.
### 3. Build the Project
After importing the project, you need to build it using Gradle.

1. Using the Command Line: Run the following command in the root project folder:

```bash
./gradlew desktop:run
```
2. Using IntelliJ or Eclipse: You can use the "Run" configuration for the desktop project module, which will compile and launch the game for you.
### 4. Running the game
- **Command line**: Navigate to the project directory and run:
```bash
./gradlew desktop:run
```
- **IDE**: Select the desktop configuration and run the game.
