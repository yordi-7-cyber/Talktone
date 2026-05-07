# ብዕር (Biir) - Ethiopian Literature Learning App

## Overview

**ብዕር (Biir)** is a comprehensive Android application designed to preserve, promote, and teach Ethiopian literature and culture through modern mobile technology. The app serves as a digital library and learning platform for Ethiopian literary content including poems, folktales, proverbs, novels, and more.

---

## App Features

### 1. User Authentication & Onboarding
- **Bilingual Registration**: Full support for both Amharic and English during registration
- **Login System**: Returning users can log in with their credentials
- **Profile Picture Upload**: Users can optionally upload their profile photo
- **Language Preference**: Choose Amharic-only or bilingual (Amharic + English) interface
- **Level Selection**: 
  - Beginner (ጀማሪ) - Learn alphabet, numbers, basic words
  - Intermediate (መካከለኛ) - Poems, folktales, proverbs
  - Advanced (ከፍተኛ) - Novels, creative writing
- **Role Selection**: Reader or Creator mode

### 2. Categories (Sidebar Navigation)
All categories are accessible through a beautiful sidebar drawer:

| Category | Amharic | Description |
|----------|---------|-------------|
| 📜 Poems | ግጥም | Traditional and modern Ethiopian poetry |
| 🦁 Folktales | ተረት | Traditional Ethiopian stories and fables |
| 💬 Proverbs | ምሳሌያዊ ንግግር | Wise sayings and proverbs |
| 📖 Novels | ልቦለድ | Ethiopian literature and novels |
| 🧠 Quiz | ፈተና | Knowledge testing games |
| 📚 Books | መጽሐፍ አንባቢ | PDF/EPUB book reader |
| ✨ Quotes | ጥቅሶች | Inspirational quotes |
| 🎙️ Podcast | ፖድካስት | Audio content links |

### 3. Content Library

#### Poems (15+ poems)
Including famous works like:
- **ክራር እና ፍቅር** (Sorrow and Love) by ኤፍሬም ስዩም
- **የተጣለ ፈረስ** (The Abandoned Horse) by በእውቀቱ ስዩም
- **ኢትዮጵያ** (Ethiopia) - Patriotic poem
- **ፍቅር** (Love) by ፀጋዬ ገብረ መድህን
- And many more...

#### Folktales (20+ stories)
Including:
- **ኮከብን መያዝ** (Catching a Star) - Story of Lili learning about dreams
- **ንግሥት ማክዳና የጥበብ ፍለጋ** - Queen Makeda and King Solomon
- **አንበሳና አይጥ** (The Lion and the Mouse)
- Traditional Ethiopian fables with moral lessons

#### Proverbs (20+ proverbs)
Ethiopian wisdom including:
- "ዕውቀት ሀብት ነው" (Knowledge is wealth)
- "ፍቅር ሁሉን ያሸንፋል" (Love conquers all)
- "ትዕግስት ፍሬ ያፈራል" (Patience bears fruit)
- And many more...

### 4. Interactive Games

#### Word Puzzle Game (የቃላት ጦርነት)
- 10 Amharic word puzzles
- Players unscramble letters to form Amharic words
- English translation hints provided
- Score tracking system

#### Quiz System
- 10+ knowledge questions about Ethiopia
- Multiple choice format
- Immediate feedback with explanations
- Score tracking and progress

### 5. Book Reader
- Import PDF and EPUB files
- Bookmark pages
- Reading progress tracking
- Page navigation

### 6. Reading Streak System
- **60-minute timer logic**: Streak counts after 60 minutes of reading
- Daily streak tracking
- Achievement notifications (7-day streaks)
- Motivational progress display

### 7. Creator Submission System
- Users with "Creator" role can submit:
  - Poems
  - Folktales
  - Proverbs
- Admin approval workflow
- Approved content automatically appears in categories

### 8. Admin Dashboard
- Secure admin login (password: @Admin1996)
- Content management:
  - Add/Edit/Delete poems
  - Add/Edit/Delete folktales
  - Add/Edit/Delete proverbs
  - Add/Edit/Delete quiz questions
  - Add/Edit/Delete quotes
- Review and approve/reject creator submissions
- All approved content automatically integrates into the app

### 9. Beginner Learning Mode
- **Abugida (Ge'ez script) lessons**: Complete Ethiopian alphabet
- Number learning (1-100 in Ge'ez numerals)
- Interactive character display
- Progress tracking

### 10. Text-to-Speech
- Amharic TTS support for listening to content
- Available in poem, folktale, and proverb detail screens

---

## Technical Architecture

### Technology Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Database (Local SQLite)
- **Async**: Kotlin Coroutines & Flow
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation Compose
- **Data Storage**: DataStore Preferences

### Database Schema
```
├── UserProfile (user data, settings)
├── ReadingStreak (daily reading tracking)
├── BookEntity (imported books)
├── BookmarkEntity (book bookmarks)
├── CreatorSubmission (user submissions)
├── AdminPoem (admin-added poems)
├── AdminTeret (admin-added folktales)
├── AdminMisale (admin-added proverbs)
├── AdminQuiz (admin-added quiz questions)
└── AdminQuote (admin-added quotes)
```

### Key Features Implementation

#### Automatic Content Integration
When admin adds content or approves creator submissions:
1. Content is saved to the appropriate database table
2. Automatically appears in the relevant category list
3. No app restart required - Flow-based reactive updates

#### Sidebar Navigation
- ModalNavigationDrawer component
- Image-backed category cards
- Profile display with photo
- Logout functionality
- Settings access

---

## Installation Requirements

### Minimum Requirements
- Android 7.0 (API 24) or higher
- 50MB storage space
- Internet connection for initial setup

### Build Requirements
- Android Studio Hedgehog or later
- JDK 11
- Kotlin 1.9+
- Gradle 8.0+

---

## How to Build

1. Open project in Android Studio
2. Sync Gradle files
3. Build → Make Project
4. Run on emulator or physical device

```bash
# Command line build
./gradlew assembleDebug
```

---

## App Screenshots Description

1. **Splash Screen**: Beautiful icon with animated entrance
2. **Welcome Screen**: Bilingual welcome with Register/Login options
3. **Registration**: Profile photo upload, name, phone input
4. **Level Selection**: Visual cards for Beginner/Intermediate/Advanced
5. **Home Screen**: Welcome card, streak display, quick actions
6. **Sidebar Drawer**: Categories, profile, logout
7. **Literature List**: Cards with image backgrounds
8. **Content Detail**: Full content with TTS button
9. **Word Puzzle**: Interactive letter grid game
10. **Admin Dashboard**: Tabbed content management

---

## Color Theme

| Color | Hex Code | Usage |
|-------|----------|-------|
| Ethiopian Gold | #D4AF37 | Primary accent, buttons, highlights |
| Dark Purple | #1A0A2E | Background, cards |
| Deep Navy | #0D0221 | Drawer background |
| White | #FFFFFF | Text, icons |

---

## Future Enhancements

1. **Audio Content**: Recorded poems and stories
2. **Offline Mode**: Full offline content access
3. **Social Features**: Share content, comments
4. **Achievements**: Gamification badges
5. **Cloud Sync**: Backup user progress
6. **More Languages**: Tigrinya, Oromo support

---

## Credits

- **App Development**: Kiro AI Assistant
- **Content Sources**: Ethiopian literary tradition
- **Poetry**: Famous Ethiopian poets including Efrem Seyoum, Tsegaye Gebre-Medhin
- **Folktales**: Traditional Ethiopian oral tradition

---

## Contact & Support

For questions or support, please contact the development team.

---

**Version**: 1.0  
**Last Updated**: May 2025  
**Package Name**: com.example.talktone
