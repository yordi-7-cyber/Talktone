package com.example.talktone.data

data class LiteratureItem(
    val id: Int,
    val titleAm: String,
    val titleEn: String,
    val contentAm: String,
    val contentEn: String,
    val author: String = "",
    val category: LiteratureCategory
)

data class QuizQuestion(
    val id: Int,
    val questionAm: String,
    val questionEn: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanationAm: String = "",
    val explanationEn: String = ""
)

data class QuoteItem(
    val id: Int,
    val textAm: String,
    val textEn: String,
    val author: String,
    val backgroundGradient: List<Long>
)

enum class LiteratureCategory(val labelAm: String, val labelEn: String, val emoji: String) {
    POEM("ግጥም", "Poems", "📜"),
    TERET("ተረት", "Folktales", "🦁"),
    MISALE("ምሳሌያዊ ንግግር", "Proverbs", "💬"),
    QUIZ("ፈተና", "Quiz", "🧠"),
    BOOKS("መጽሐፍ አንባቢ", "Book Reader", "📚"),
    QUOTES("ጥቅሶች", "Quotes", "✨")
}

object AmharicContent {

    val poems = listOf(
        LiteratureItem(
            id = 1,
            titleAm = "ኢትዮጵያ",
            titleEn = "Ethiopia",
            contentAm = """ኢትዮጵያ ሆይ ደስ ይበልሽ
ልጆችሽ ሁሉ ይወዱሽ
ታሪክሽ ታላቅ ነው
ክብርሽ ዘላለም ይኑር

ጥቁር ምድር ቅዱስ ሀገር
ሰማዩ ሰማያዊ ነው
ሸለቆሽ አረንጓዴ ነው
ፍቅርሽ ዘላለም ይኑር""",
            contentEn = """O Ethiopia, rejoice
All your children love you
Your history is great
May your glory last forever

Black land, holy country
Your sky is blue
Your valleys are green
May your love last forever""",
            author = "ሕዝባዊ",
            category = LiteratureCategory.POEM
        ),
        LiteratureItem(
            id = 2,
            titleAm = "ፍቅር",
            titleEn = "Love",
            contentAm = """ፍቅር ምንድን ነው?
ልብ ሲሞቅ ሲቀልጥ
ዓይን ሲያበራ ሲደምቅ
ነፍስ ሲፈነዳ ሲደሰት

ፍቅር ምንድን ነው?
ሁሉን ነገር ሲሰጥ
ምንም ሳይጠብቅ ሲወድ
ዘላለም ሳይሰለቸው ሲቆም""",
            contentEn = """What is love?
When the heart warms and melts
When eyes shine and glow
When the soul bursts with joy

What is love?
When it gives everything
Loving without expecting anything
Standing forever without tiring""",
            author = "ፀጋዬ ገብረ መድህን",
            category = LiteratureCategory.POEM
        ),
        LiteratureItem(
            id = 3,
            titleAm = "ጠዋት",
            titleEn = "Morning",
            contentAm = """ጠዋት ሲነጋ ሲቀድ
ወፎች ሲዘምሩ ሲጫወቱ
ፀሐይ ሲወጣ ሲደምቅ
ሕይወት ሲጀምር ሲቀጥል

አዲስ ቀን አዲስ ተስፋ
አዲስ ሕልም አዲስ ፍቅር
ሁሉ ነገር ይቻላል
ዛሬ ቀን ነው ለሥራ""",
            contentEn = """When morning breaks and dawns
Birds singing and playing
Sun rising and shining
Life beginning and continuing

New day, new hope
New dream, new love
Everything is possible
Today is the day for work""",
            author = "ሕዝባዊ",
            category = LiteratureCategory.POEM
        ),
        LiteratureItem(
            id = 4,
            titleAm = "አዲስ አበባ",
            titleEn = "Addis Ababa",
            contentAm = """አዲስ አበባ ከተማዬ
አበቦች የሚፈኩበት
ሕዝቦች የሚኖሩበት
ፍቅር የሚሰፍንበት

ኢንቶቶ ተራራ ላይ
ሸለቆ ሸለቆ ውስጥ
ቡና ሲፈላ ሲሸት
ቤቴ ነው ልቤ ነው""",
            contentEn = """Addis Ababa, my city
Where flowers bloom
Where people live
Where love reigns

On Entoto mountain
In valley after valley
When coffee brews and smells
It is my home, my heart""",
            author = "ሕዝባዊ",
            category = LiteratureCategory.POEM
        ),
        LiteratureItem(
            id = 5,
            titleAm = "ትምህርት",
            titleEn = "Education",
            contentAm = """ትምህርት ብርሃን ነው
ጨለማን የሚያስወግድ
ድህነትን የሚያሸንፍ
ሕይወትን የሚቀይር

አንብብ ተማር ሥራ
ዕውቀት ሀብት ነው
ማንም ሊወስደው አይችልም
ዘላለም ከአንተ ጋር ነው""",
            contentEn = """Education is light
That removes darkness
That conquers poverty
That changes life

Read, learn, work
Knowledge is wealth
No one can take it away
It is with you forever""",
            author = "ሕዝባዊ",
            category = LiteratureCategory.POEM
        )
    )

    val terets = listOf(
        LiteratureItem(
            id = 10,
            titleAm = "አንበሳና አይጥ",
            titleEn = "The Lion and the Mouse",
            contentAm = """ከዘመናት በፊት አንድ ታላቅ አንበሳ በጫካ ውስጥ ይኖር ነበር። አንድ ቀን ትንሽ አይጥ ሲጫወት አንበሳው ላይ ወደቀ። አንበሳው ተናደደ።

"ትንሽ አይጥ! ልበላህ!" አለ አንበሳው።

"እባክህ ይቅር በለኝ!" አለ አይጡ። "አንድ ቀን ልረዳህ እችላለሁ።"

አንበሳው ሳቀ። "አንተ ትንሽ አይጥ ልትረዳኝ?" ግን ይቅር አለው።

ከጥቂት ቀናት በኋላ አንበሳው በአዳኞች ወጥመድ ተያዘ። አይጡ ሰምቶ ሮጠ። ገመዱን ቆረጠ። አንበሳው ነፃ ወጣ።

"አመሰግናለሁ ትንሽ ጓደኛዬ!" አለ አንበሳው።

ትምህርቱ፡ ትንሽ ቢሆን ደግነት ትልቅ ዋጋ አለው።""",
            contentEn = """Long ago, a great lion lived in the forest. One day, a small mouse fell on the lion while playing. The lion was angry.

"Little mouse! I will eat you!" said the lion.

"Please forgive me!" said the mouse. "One day I can help you."

The lion laughed. "You, a tiny mouse, help me?" But he let it go.

A few days later, the lion was caught in a hunter's trap. The mouse heard and ran. It chewed through the rope. The lion was free.

"Thank you, my little friend!" said the lion.

Lesson: Even small acts of kindness have great value.""",
            author = "ሕዝባዊ ተረት",
            category = LiteratureCategory.TERET
        ),
        LiteratureItem(
            id = 11,
            titleAm = "ሁለት ወንድሞች",
            titleEn = "Two Brothers",
            contentAm = """ሁለት ወንድሞች አብረው ይኖሩ ነበር። አንደኛው ሀብታም ሌላኛው ደሃ ነበር። ሀብታሙ ወንድም ሁሌ ለራሱ ብቻ ያስብ ነበር።

አንድ ቀን ደሃው ወንድም ታመመ። ሀብታሙ ወንድም ሊረዳው አልፈለገም።

ጎረቤቶቹ ግን ደሃውን ወንድም ረዱ። ምግብ አመጡ። ሐኪም ጠሩ።

ደሃው ወንድም ዳነ። ሀብታሙ ወንድም ተፀፀተ።

"ይቅርታ ወንድሜ" አለ። "ከዛሬ ጀምሮ አብረን እንኖራለን።"

ትምህርቱ፡ ቤተሰብ ከሁሉ ይበልጣል።""",
            contentEn = """Two brothers lived together. One was rich, the other poor. The rich brother always thought only of himself.

One day the poor brother fell ill. The rich brother did not want to help.

But the neighbors helped the poor brother. They brought food. They called a doctor.

The poor brother recovered. The rich brother felt remorse.

"Forgive me, brother," he said. "From today we will live together."

Lesson: Family is above all.""",
            author = "ሕዝባዊ ተረት",
            category = LiteratureCategory.TERET
        ),
        LiteratureItem(
            id = 12,
            titleAm = "ቀበሮና ወፍ",
            titleEn = "The Fox and the Bird",
            contentAm = """አንድ ቀበሮ ዛፍ ላይ ሥጋ ይዛ ቁጭ ያለች ወፍ አየ። ሥጋዋን ሊወስድ ፈለገ።

"ወፍ ሆይ! ድምፅሽ ምን ያህል ቆንጆ ነው! ዘምሪልኝ!" አለ ቀበሮው።

ወፉ ደስ ብሏት ዘምራ ሥጋዋ ወደቀ። ቀበሮው ሥጋዋን ወሰደ።

"ሞኝ ወፍ!" አለ ቀበሮው ሲሄድ።

ትምህርቱ፡ ሽሙጥ ሲሰሙ ጥንቃቄ አድርጉ።""",
            contentEn = """A fox saw a bird sitting in a tree holding meat. He wanted to take her meat.

"O bird! How beautiful your voice is! Sing for me!" said the fox.

The bird was pleased and sang, and her meat fell. The fox took her meat.

"Foolish bird!" said the fox as he left.

Lesson: Be careful when you hear flattery.""",
            author = "ሕዝባዊ ተረት",
            category = LiteratureCategory.TERET
        )
    )

    val misaleoch = listOf(
        LiteratureItem(
            id = 20,
            titleAm = "ዕውቀት ሀብት ነው",
            titleEn = "Knowledge is Wealth",
            contentAm = "ዕውቀት ሀብት ነው ማንም ሊወስደው አይችልም።",
            contentEn = "Knowledge is wealth that no one can take away.",
            author = "ሕዝባዊ",
            category = LiteratureCategory.MISALE
        ),
        LiteratureItem(
            id = 21,
            titleAm = "አንድ ለሁሉ ሁሉ ለአንድ",
            titleEn = "One for All, All for One",
            contentAm = "አንድ ለሁሉ ሁሉ ለአንድ — ይህ ነው የኢትዮጵያ መንፈስ።",
            contentEn = "One for all, all for one — this is the spirit of Ethiopia.",
            author = "ሕዝባዊ",
            category = LiteratureCategory.MISALE
        ),
        LiteratureItem(
            id = 22,
            titleAm = "ዛፍ ሲያድግ ፍሬ ይሰጣል",
            titleEn = "A Growing Tree Bears Fruit",
            contentAm = "ዛፍ ሲያድግ ፍሬ ይሰጣል — ሰው ሲማር ሀብት ያፈራል።",
            contentEn = "A growing tree bears fruit — a learning person creates wealth.",
            author = "ሕዝባዊ",
            category = LiteratureCategory.MISALE
        ),
        LiteratureItem(
            id = 23,
            titleAm = "ቸኩሎ ሲሄድ ዘግይቶ ይደርሳል",
            titleEn = "Haste Makes Waste",
            contentAm = "ቸኩሎ ሲሄድ ዘግይቶ ይደርሳል።",
            contentEn = "One who rushes arrives late.",
            author = "ሕዝባዊ",
            category = LiteratureCategory.MISALE
        ),
        LiteratureItem(
            id = 24,
            titleAm = "ፍቅር ሁሉን ያሸንፋል",
            titleEn = "Love Conquers All",
            contentAm = "ፍቅር ሁሉን ያሸንፋል — ጦርነትም ቢሆን።",
            contentEn = "Love conquers all — even war.",
            author = "ሕዝባዊ",
            category = LiteratureCategory.MISALE
        ),
        LiteratureItem(
            id = 25,
            titleAm = "ዝምታ ወርቅ ነው",
            titleEn = "Silence is Gold",
            contentAm = "ዝምታ ወርቅ ነው — ቃል ብር ነው።",
            contentEn = "Silence is gold — words are silver.",
            author = "ሕዝባዊ",
            category = LiteratureCategory.MISALE
        ),
        LiteratureItem(
            id = 26,
            titleAm = "ጤና ሀብት ነው",
            titleEn = "Health is Wealth",
            contentAm = "ጤና ሀብት ነው — ሀብት ጤና አይደለም።",
            contentEn = "Health is wealth — wealth is not health.",
            author = "ሕዝባዊ",
            category = LiteratureCategory.MISALE
        ),
        LiteratureItem(
            id = 27,
            titleAm = "ትዕግስት ፍሬ ያፈራል",
            titleEn = "Patience Bears Fruit",
            contentAm = "ትዕግስት ፍሬ ያፈራል — ቸኩሎ ሲሄድ ይወድቃል።",
            contentEn = "Patience bears fruit — the hasty one falls.",
            author = "ሕዝባዊ",
            category = LiteratureCategory.MISALE
        )
    )

    val quizQuestions = listOf(
        QuizQuestion(
            id = 1,
            questionAm = "የኢትዮጵያ ዋና ከተማ የትኛው ነው?",
            questionEn = "What is the capital city of Ethiopia?",
            options = listOf("አዲስ አበባ", "ባህር ዳር", "ሐዋሳ", "ድሬዳዋ"),
            correctIndex = 0,
            explanationAm = "አዲስ አበባ የኢትዮጵያ ዋና ከተማ ነው።",
            explanationEn = "Addis Ababa is the capital city of Ethiopia."
        ),
        QuizQuestion(
            id = 2,
            questionAm = "ኢትዮጵያ ስንት ክልሎች አሏት?",
            questionEn = "How many regions does Ethiopia have?",
            options = listOf("10", "11", "12", "14"),
            correctIndex = 3,
            explanationAm = "ኢትዮጵያ 14 ክልሎች አሏት።",
            explanationEn = "Ethiopia has 14 regions."
        ),
        QuizQuestion(
            id = 3,
            questionAm = "የአድዋ ጦርነት የተካሄደው መቼ ነው?",
            questionEn = "When did the Battle of Adwa take place?",
            options = listOf("1896", "1900", "1889", "1910"),
            correctIndex = 0,
            explanationAm = "የአድዋ ጦርነት በ1896 ዓ.ም ተካሄደ።",
            explanationEn = "The Battle of Adwa took place in 1896."
        ),
        QuizQuestion(
            id = 4,
            questionAm = "ቡና የተገኘው ከየት ነው?",
            questionEn = "Where was coffee discovered?",
            options = listOf("ብራዚል", "ኢትዮጵያ", "ኮሎምቢያ", "ቬትናም"),
            correctIndex = 1,
            explanationAm = "ቡና ለመጀመሪያ ጊዜ የተገኘው ኢትዮጵያ ውስጥ ነው።",
            explanationEn = "Coffee was first discovered in Ethiopia."
        ),
        QuizQuestion(
            id = 5,
            questionAm = "ሉሲ (ዲንቅነሽ) ምን ዓይነት ፍጡር ነበረች?",
            questionEn = "What kind of creature was Lucy (Dinknesh)?",
            options = listOf("ዳይኖሰር", "ቀደምት ሰው", "ዝንጀሮ", "ዓሣ"),
            correctIndex = 1,
            explanationAm = "ሉሲ (ዲንቅነሽ) ቀደምት ሰው ነበረች።",
            explanationEn = "Lucy (Dinknesh) was an early human ancestor."
        ),
        QuizQuestion(
            id = 6,
            questionAm = "የኢትዮጵያ ብሔራዊ ቋንቋ ምንድን ነው?",
            questionEn = "What is the national language of Ethiopia?",
            options = listOf("ኦሮምኛ", "ትግርኛ", "አማርኛ", "ሶማልኛ"),
            correctIndex = 2,
            explanationAm = "አማርኛ የኢትዮጵያ ብሔራዊ ቋንቋ ነው።",
            explanationEn = "Amharic is the national language of Ethiopia."
        ),
        QuizQuestion(
            id = 7,
            questionAm = "ጥቁር አባይ ወይም ዓባይ ወንዝ ከየት ይወጣል?",
            questionEn = "Where does the Blue Nile (Abay) river originate?",
            options = listOf("ሐይቅ ጣና", "ሐይቅ ዚዋይ", "ሐይቅ ቱርካና", "ሐይቅ ሻላ"),
            correctIndex = 0,
            explanationAm = "ዓባይ ወንዝ ከሐይቅ ጣና ይወጣል።",
            explanationEn = "The Blue Nile originates from Lake Tana."
        ),
        QuizQuestion(
            id = 8,
            questionAm = "ፀጋዬ ገብረ መድህን ምን ዓይነት ጸሐፊ ነበሩ?",
            questionEn = "What kind of writer was Tsegaye Gebre-Medhin?",
            options = listOf("ልቦለድ ጸሐፊ", "ገጣሚ እና ተውኔት ጸሐፊ", "ታሪክ ጸሐፊ", "ጋዜጠኛ"),
            correctIndex = 1,
            explanationAm = "ፀጋዬ ገብረ መድህን ታዋቂ ኢትዮጵያዊ ገጣሚ እና ተውኔት ጸሐፊ ነበሩ።",
            explanationEn = "Tsegaye Gebre-Medhin was a renowned Ethiopian poet and playwright."
        ),
        QuizQuestion(
            id = 9,
            questionAm = "ቀዳማዊ ኃይለ ሥላሴ ስልጣን የያዙት መቼ ነው?",
            questionEn = "When did Emperor Haile Selassie come to power?",
            options = listOf("1930", "1935", "1941", "1955"),
            correctIndex = 0,
            explanationAm = "ቀዳማዊ ኃይለ ሥላሴ በ1930 ዓ.ም ስልጣን ያዙ።",
            explanationEn = "Emperor Haile Selassie came to power in 1930."
        ),
        QuizQuestion(
            id = 10,
            questionAm = "ኢትዮጵያ ስንት ዓመት ታሪክ አላት?",
            questionEn = "How many years of history does Ethiopia have?",
            options = listOf("1000 ዓመት", "2000 ዓመት", "3000 ዓመት", "3000+ ዓመት"),
            correctIndex = 3,
            explanationAm = "ኢትዮጵያ ከ3000 ዓመት በላይ ታሪክ አላት።",
            explanationEn = "Ethiopia has more than 3000 years of history."
        )
    )

    val quotes = listOf(
        QuoteItem(
            id = 1,
            textAm = "ዕውቀት ሀብት ነው ማንም ሊወስደው አይችልም።",
            textEn = "Knowledge is wealth that no one can take away.",
            author = "ሕዝባዊ",
            backgroundGradient = listOf(0xFF1A0A2E, 0xFF16213E)
        ),
        QuoteItem(
            id = 2,
            textAm = "ፍቅር ሁሉን ያሸንፋል።",
            textEn = "Love conquers all.",
            author = "ሕዝባዊ",
            backgroundGradient = listOf(0xFF8B0000, 0xFF4A0000)
        ),
        QuoteItem(
            id = 3,
            textAm = "ትምህርት ብርሃን ነው ጨለማን ያስወግዳል።",
            textEn = "Education is light that removes darkness.",
            author = "ሕዝባዊ",
            backgroundGradient = listOf(0xFF0D3B2E, 0xFF1A5C45)
        ),
        QuoteItem(
            id = 4,
            textAm = "ትዕግስት ፍሬ ያፈራል።",
            textEn = "Patience bears fruit.",
            author = "ሕዝባዊ",
            backgroundGradient = listOf(0xFF2C1810, 0xFF5C3317)
        ),
        QuoteItem(
            id = 5,
            textAm = "ኢትዮጵያ ለዘላለም ትኑር።",
            textEn = "May Ethiopia live forever.",
            author = "ሕዝባዊ",
            backgroundGradient = listOf(0xFF006400, 0xFF8B0000)
        ),
        QuoteItem(
            id = 6,
            textAm = "አንብቦ ያደገ ሰው ዓለምን ያሸንፋል።",
            textEn = "One who grows through reading conquers the world.",
            author = "ሕዝባዊ",
            backgroundGradient = listOf(0xFF1A1A2E, 0xFF16213E)
        ),
        QuoteItem(
            id = 7,
            textAm = "ዛሬ ያልሠራህ ነገ ያዝናናል።",
            textEn = "What you don't do today will grieve you tomorrow.",
            author = "ሕዝባዊ",
            backgroundGradient = listOf(0xFF2D1B69, 0xFF11998E)
        ),
        QuoteItem(
            id = 8,
            textAm = "ቤተሰብ ከሁሉ ይበልጣል።",
            textEn = "Family is above all.",
            author = "ሕዝባዊ",
            backgroundGradient = listOf(0xFF4A0E8F, 0xFF9B59B6)
        )
    )
}
