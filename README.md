# 🧙‍♂️ Tri-Wizard Quest - Harry Potter Maze Game

> **Introduction to Java Programming - NYU Tandon School of Engineering**  
> **Final Project** | **Harry Potter Themed Maze Adventure Game**

An immersive **Harry Potter Tri-Wizard Tournament** themed maze game built in Java using Swing GUI. Navigate through dynamically generated mazes, avoid dragons, collect magical items, and complete the Tri-Wizard Quest!

## 🎮 Game Overview

Experience the magic of the Tri-Wizard Tournament as you navigate through challenging mazes filled with:
- **Dynamic maze generation** with increasing difficulty
- **Dragon enemies** that hunt you down
- **Magical items** to collect for points
- **Multiple levels** with progressive challenges
- **Sound effects** and **visual feedback**
- **Score tracking** and **lives system**

## 🎯 Key Features

- ✅ **Procedural Maze Generation**: Advanced algorithms create unique mazes
- ✅ **Enemy AI**: Intelligent dragon movement and hunting behavior
- ✅ **Player Mechanics**: Smooth movement with collision detection
- ✅ **Sound Integration**: Immersive audio with victory and defeat sounds
- ✅ **GUI Design**: Professional Swing-based user interface
- ✅ **Level Progression**: Increasing difficulty with new challenges
- ✅ **Score System**: Track your performance across levels
- ✅ **Harry Potter Theme**: Authentic magical atmosphere

## 🛠️ Technical Implementation

### Core Architecture
- **GameManager**: Main game loop and state management
- **MazeGenerator**: Procedural maze creation algorithms
- **GameDesign**: Swing GUI implementation and rendering
- **Player & Enemy**: Character mechanics and AI
- **SoundManager**: Audio integration and playback

### Key Classes
- `GameManager.java` - Main game controller
- `Maze.java` - Maze logic and navigation
- `MazeGenerator.java` - Procedural generation algorithms
- `GameDesign.java` - GUI rendering and display
- `Player.java` - Player character mechanics
- `Enemy.java` - Dragon AI and behavior
- `Controller.java` - Input handling and game controls

## 🚀 Quick Start

### Prerequisites
- **Java 11 or higher**
- **Eclipse IDE** (recommended for development)
- **Audio support** for sound effects

### Installation & Running

#### Method 1: Eclipse IDE
1. **Clone the repository**
   ```bash
   git clone https://github.com/aaftab007/tri-wizard-quest.git
   cd tri-wizard-quest
   ```

2. **Import into Eclipse**
   - Open Eclipse IDE
   - File → Import → Existing Projects into Workspace
   - Select the project folder
   - Click Finish

3. **Run the game**
   - Right-click on `Run.java`
   - Run As → Java Application

#### Method 2: Command Line
```bash
# Compile the project
javac -cp . src/*.java

# Run the game
java -cp src Run
```

### Game Controls
- **Arrow Keys**: Move the player character
- **ESC**: Pause/Resume game
- **R**: Restart current level
- **Q**: Quit game

## 🎨 Game Mechanics

### Maze Generation
The game uses advanced algorithms to generate unique mazes:
- **Recursive Backtracking**: Creates complex, solvable mazes
- **Dynamic Difficulty**: Maze complexity increases with levels
- **Pathfinding**: Ensures all areas are accessible

### Enemy AI
Dragons exhibit intelligent behavior:
- **Pathfinding**: Dragons navigate around obstacles
- **Hunting**: Dragons actively pursue the player
- **Spawn Management**: Strategic enemy placement

### Player System
- **Movement**: Smooth, responsive controls
- **Collision Detection**: Accurate boundary checking
- **Lives System**: Multiple attempts per level
- **Score Tracking**: Points for items collected and survival time

## 📊 Project Structure

```
Tri-Wizard Quest/
├── src/
│   ├── Run.java                 # Main entry point
│   ├── GameManager.java         # Game loop and state management
│   ├── Game.java               # Core game logic
│   ├── GameDesign.java         # GUI implementation
│   ├── Maze.java               # Maze logic and navigation
│   ├── MazeGenerator.java      # Procedural generation
│   ├── Player.java             # Player character
│   ├── Enemy.java              # Dragon AI
│   ├── Controller.java         # Input handling
│   ├── Tile.java               # Maze tile system
│   ├── Picture.java            # Image handling
│   ├── OptionPanel.java        # UI dialogs
│   └── InstructionDesign.java  # Game instructions
├── pictures/                   # Game assets and sprites
├── sounds/                     # Audio files
├── README.md                   # This file
├── LICENSE                     # MIT License
└── .gitignore                 # Git ignore patterns
```

## 🎯 Learning Objectives

This project demonstrates mastery of:
- **Object-Oriented Programming** principles
- **Java Swing GUI** development
- **Game development** concepts
- **Algorithm implementation** (maze generation, pathfinding)
- **Event-driven programming**
- **Audio integration**
- **Collision detection** systems
- **State management** in games

## 🏆 Technical Achievements

### Advanced Algorithms
- **Recursive Maze Generation**: Creates complex, solvable mazes
- **A* Pathfinding**: Efficient enemy movement algorithms
- **Collision Detection**: Precise boundary checking
- **State Management**: Robust game state handling

### Software Engineering
- **Modular Design**: Clean separation of concerns
- **Event-Driven Architecture**: Responsive user interface
- **Resource Management**: Efficient memory and asset handling
- **Error Handling**: Robust exception management

## 🎮 Game Features

### Visual Elements
- **Dynamic Maze Rendering**: Real-time maze display
- **Character Sprites**: Animated player and enemy graphics
- **UI Elements**: Professional game interface
- **Visual Feedback**: Clear game state indicators

### Audio Experience
- **Victory Sounds**: Celebratory audio on level completion
- **Defeat Sounds**: Dramatic audio on player death
- **Background Audio**: Immersive game atmosphere

### Gameplay Mechanics
- **Progressive Difficulty**: Increasingly challenging levels
- **Score System**: Points for survival and item collection
- **Lives Management**: Multiple attempts per level
- **Level Progression**: Seamless level transitions

## 🚀 Future Enhancements

### Planned Features
- [ ] **Multiple Characters**: Choose different wizards
- [ ] **Power-ups**: Magical spells and abilities
- [ ] **Multiplayer Mode**: Competitive gameplay
- [ ] **Level Editor**: Custom maze creation
- [ ] **Save System**: Progress persistence
- [ ] **Achievements**: Unlockable content

### Technical Improvements
- [ ] **Performance Optimization**: Enhanced rendering
- [ ] **Mobile Support**: Android/iOS versions
- [ ] **Cloud Integration**: Online leaderboards
- [ ] **AI Enhancement**: More sophisticated enemy behavior

## 🧪 Testing

### Manual Testing
- **Gameplay Testing**: All game mechanics verified
- **UI Testing**: Interface responsiveness confirmed
- **Audio Testing**: Sound effects properly integrated
- **Performance Testing**: Smooth gameplay on target systems

### Quality Assurance
- **Code Review**: Peer-reviewed implementation
- **Bug Testing**: Comprehensive error checking
- **User Experience**: Intuitive controls and feedback

## 📚 Documentation

- **Code Comments**: Comprehensive inline documentation
- **Class Documentation**: Clear method descriptions
- **Architecture Overview**: System design documentation
- **User Guide**: Gameplay instructions

## 🤝 Contributing

This is an academic project, but suggestions are welcome:
- **Bug Reports**: Help improve game stability
- **Feature Requests**: Suggest new gameplay elements
- **Code Improvements**: Enhance performance and functionality
- **Documentation**: Improve project documentation

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **J.K. Rowling**: Harry Potter universe inspiration
- **NYU Tandon**: Educational framework and support
- **Java Community**: Excellent documentation and resources
- **Open Source Community**: Inspiring game development practices

## 🎓 Academic Context

- **Course**: Introduction to Java Programming
- **Institution**: NYU Tandon School of Engineering
- **Semester**: Spring 2024
- **Project Type**: Final Project
- **Team**: Individual project

---

<div align="center">

**Experience the Magic of the Tri-Wizard Tournament!** 🧙‍♂️⚡

*Built with ❤️ using Java and Swing*

</div>
