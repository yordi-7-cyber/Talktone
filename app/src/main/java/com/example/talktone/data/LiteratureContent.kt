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

data class PodcastItem(
    val id: Int,
    val titleAm: String,
    val titleEn: String,
    val channelName: String,
    val youtubeUrl: String,
    val thumbnailEmoji: String = "🎙️",
    val description: String = ""
)

data class AbugidaRow(
    val base: String,
    val forms: List<String>
)

enum class LiteratureCategory(val labelAm: String, val labelEn: String, val emoji: String) {
    POEM("ግጥም", "Poems", "📜"),
    TERET("ተረት", "Folktales", "🦁"),
    MISALE("ምሳሌያዊ ንግግር", "Proverbs", "💬"),
    NOVEL("ልቦለድ", "Novels", "📖"),
    QUIZ("ፈተና", "Quiz", "🧠"),
    BOOKS("መጽሐፍ አንባቢ", "Book Reader", "📚"),
    QUOTES("ጥቅሶች", "Quotes", "✨"),
    PODCAST("ፖድካስት", "Podcast", "🎙️")
}

object AmharicContent {

    // ── Abugida for beginners ─────────────────────────────────────────────────
    val abugidaRows = listOf(
        AbugidaRow("ሀ", listOf("ሀ","ሁ","ሂ","ሃ","ሄ","ህ","ሆ")),
        AbugidaRow("ለ", listOf("ለ","ሉ","ሊ","ላ","ሌ","ል","ሎ")),
        AbugidaRow("ሐ", listOf("ሐ","ሑ","ሒ","ሓ","ሔ","ሕ","ሖ")),
        AbugidaRow("መ", listOf("መ","ሙ","ሚ","ማ","ሜ","ም","ሞ")),
        AbugidaRow("ሠ", listOf("ሠ","ሡ","ሢ","ሣ","ሤ","ሥ","ሦ")),
        AbugidaRow("ረ", listOf("ረ","ሩ","ሪ","ራ","ሬ","ር","ሮ")),
        AbugidaRow("ሰ", listOf("ሰ","ሱ","ሲ","ሳ","ሴ","ስ","ሶ")),
        AbugidaRow("ሸ", listOf("ሸ","ሹ","ሺ","ሻ","ሼ","ሽ","ሾ")),
        AbugidaRow("ቀ", listOf("ቀ","ቁ","ቂ","ቃ","ቄ","ቅ","ቆ")),
        AbugidaRow("በ", listOf("በ","ቡ","ቢ","ባ","ቤ","ብ","ቦ")),
        AbugidaRow("ተ", listOf("ተ","ቱ","ቲ","ታ","ቴ","ት","ቶ")),
        AbugidaRow("ቸ", listOf("ቸ","ቹ","ቺ","ቻ","ቼ","ች","ቾ")),
        AbugidaRow("ነ", listOf("ነ","ኑ","ኒ","ና","ኔ","ን","ኖ")),
        AbugidaRow("አ", listOf("አ","ኡ","ኢ","ኣ","ኤ","እ","ኦ")),
        AbugidaRow("ከ", listOf("ከ","ኩ","ኪ","ካ","ኬ","ክ","ኮ")),
        AbugidaRow("ወ", listOf("ወ","ዉ","ዊ","ዋ","ዌ","ው","ዎ")),
        AbugidaRow("ዘ", listOf("ዘ","ዙ","ዚ","ዛ","ዜ","ዝ","ዞ")),
        AbugidaRow("የ", listOf("የ","ዩ","ዪ","ያ","ዬ","ይ","ዮ")),
        AbugidaRow("ደ", listOf("ደ","ዱ","ዲ","ዳ","ዴ","ድ","ዶ")),
        AbugidaRow("ጀ", listOf("ጀ","ጁ","ጂ","ጃ","ጄ","ጅ","ጆ")),
        AbugidaRow("ገ", listOf("ገ","ጉ","ጊ","ጋ","ጌ","ግ","ጎ")),
        AbugidaRow("ጠ", listOf("ጠ","ጡ","ጢ","ጣ","ጤ","ጥ","ጦ")),
        AbugidaRow("ጨ", listOf("ጨ","ጩ","ጪ","ጫ","ጬ","ጭ","ጮ")),
        AbugidaRow("ጰ", listOf("ጰ","ጱ","ጲ","ጳ","ጴ","ጵ","ጶ")),
        AbugidaRow("ፀ", listOf("ፀ","ፁ","ፂ","ፃ","ፄ","ፅ","ፆ")),
        AbugidaRow("ፈ", listOf("ፈ","ፉ","ፊ","ፋ","ፌ","ፍ","ፎ")),
        AbugidaRow("ፐ", listOf("ፐ","ፑ","ፒ","ፓ","ፔ","ፕ","ፖ"))
    )

    val amharicNumbers = listOf(
        "፩" to "1", "፪" to "2", "፫" to "3", "፬" to "4", "፭" to "5",
        "፮" to "6", "፯" to "7", "፰" to "8", "፱" to "9", "፲" to "10",
        "፳" to "20", "፴" to "30", "፵" to "40", "፶" to "50",
        "፷" to "60", "፸" to "70", "፹" to "80", "፺" to "90", "፻" to "100"
    )

    val poems = listOf(
        LiteratureItem(1,"ክራር እና ፍቅር","Sorrow and Love",
            "ትግስት በሌላት በጨከነች ሌሊት\nወዳጅ ለወዳጁ ደብዳቤ ፃፈላት\n\nከጥቁር ሰማይ ላይ ሦስት ኮከቦች አየለጥቂት\nቅፅበታት ከከዋክብቱ ላይ ሀሳቡን አቆየ\n\nአንደኛዋ ኮከብ እጅግ የደመቀ ብርሃን ትረጫለች\nሁለተኛዋ ግን ደመቅ ትላለች ካንዷ ትሻላለች\nሦስተኛዋ ደሞ እርሱን ትመስላለች\n\nበሁለቱ መካከል ፈዛ ትታያለች\nእርሱን ለምትመስለው ብዕሩን አነሳ\nሁለቱን ከዋክብት ልቡን አወረሳ",
            "A beautiful love poem by Efrem Seyoum about longing, stars, and eternal love written under the night sky.",
            "ኤፍሬም ስዩም", LiteratureCategory.POEM),
        LiteratureItem(2,"የተጣለ ፈረስ","The Abandoned Horse",
            "ዘመኑ ያለፈ ጋማው የረገፈ\nየጎድን አጥንቱ ማበጠርያ መሳይ\nጠዋት ህያው ፍጡር\nሌሊት የጅብ ሲሳይ\n\nከተማው መግቢያ ላይ\nአቧራ የሚቅም\nየተጣለ ፈረስ\nአጋጥሞህ አያውቅም?",
            "A powerful poem about an abandoned horse, symbolizing those forgotten by society despite their past service.",
            "በእውቀቱ ስዩም", LiteratureCategory.POEM),
        LiteratureItem(3,"ኢትዮጵያ","Ethiopia",
            "ኢትዮጵያ ሆይ ደስ ይበልሽ\nልጆችሽ ሁሉ ይወዱሽ\nታሪክሽ ታላቅ ነው\nክብርሽ ዘላለም ይኑር\n\nጥቁር ምድር ቅዱስ ሀገር\nሰማዩ ሰማያዊ ነው\nሸለቆሽ አረንጓዴ ነው\nፍቅርሽ ዘላለም ይኑር",
            "O Ethiopia, rejoice\nAll your children love you\nYour history is great\nMay your glory last forever\n\nBlack land, holy country\nYour sky is blue\nYour valleys are green\nMay your love last forever",
            "ሕዝባዊ", LiteratureCategory.POEM),
        LiteratureItem(2,"ፍቅር","Love",
            "ፍቅር ምንድን ነው?\nልብ ሲሞቅ ሲቀልጥ\nዓይን ሲያበራ ሲደምቅ\nነፍስ ሲፈነዳ ሲደሰት\n\nፍቅር ምንድን ነው?\nሁሉን ነገር ሲሰጥ\nምንም ሳይጠብቅ ሲወድ\nዘላለም ሳይሰለቸው ሲቆም",
            "What is love?\nWhen the heart warms and melts\nWhen eyes shine and glow\nWhen the soul bursts with joy\n\nWhat is love?\nWhen it gives everything\nLoving without expecting anything\nStanding forever without tiring",
            "ፀጋዬ ገብረ መድህን", LiteratureCategory.POEM),
        LiteratureItem(3,"ጠዋት","Morning",
            "ጠዋት ሲነጋ ሲቀድ\nወፎች ሲዘምሩ ሲጫወቱ\nፀሐይ ሲወጣ ሲደምቅ\nሕይወት ሲጀምር ሲቀጥል\n\nአዲስ ቀን አዲስ ተስፋ\nአዲስ ሕልም አዲስ ፍቅር\nሁሉ ነገር ይቻላል\nዛሬ ቀን ነው ለሥራ",
            "When morning breaks and dawns\nBirds singing and playing\nSun rising and shining\nLife beginning and continuing\n\nNew day, new hope\nNew dream, new love\nEverything is possible\nToday is the day for work",
            "ሕዝባዊ", LiteratureCategory.POEM),
        LiteratureItem(4,"አዲስ አበባ","Addis Ababa",
            "አዲስ አበባ ከተማዬ\nአበቦች የሚፈኩበት\nሕዝቦች የሚኖሩበት\nፍቅር የሚሰፍንበት\n\nኢንቶቶ ተራራ ላይ\nሸለቆ ሸለቆ ውስጥ\nቡና ሲፈላ ሲሸት\nቤቴ ነው ልቤ ነው",
            "Addis Ababa, my city\nWhere flowers bloom\nWhere people live\nWhere love reigns\n\nOn Entoto mountain\nIn valley after valley\nWhen coffee brews and smells\nIt is my home, my heart",
            "ሕዝባዊ", LiteratureCategory.POEM),
        LiteratureItem(5,"ትምህርት","Education",
            "ትምህርት ብርሃን ነው\nጨለማን የሚያስወግድ\nድህነትን የሚያሸንፍ\nሕይወትን የሚቀይር\n\nአንብብ ተማር ሥራ\nዕውቀት ሀብት ነው\nማንም ሊወስደው አይችልም\nዘላለም ከአንተ ጋር ነው",
            "Education is light\nThat removes darkness\nThat conquers poverty\nThat changes life\n\nRead, learn, work\nKnowledge is wealth\nNo one can take it away\nIt is with you forever",
            "ሕዝባዊ", LiteratureCategory.POEM)
    )

    val terets = listOf(
        LiteratureItem(10,"ኮከብን መያዝ","Catching a Star",
            "በአንድ ወቅት በአንድ ጊዜ ውብ በሆነ ወንዝ አጠገብ በሚገኝ ትንሽ ቤት ውስጥ ሊሊና ወላጆቿ ይኖሩ ነበር።\n\nበየምሽቱ ሊሊና አባቷ ወንዙ ዳር ተቀምጠው አከባቢውንና ከዋክብትን የማድነቅ ልምድ አላቸው።\n\nአንድ ቀን ምሽት እንደሁልግዜው ወንዙ ዳር ተቀምጠው ተፈጥሮን እያደንቁ እያለ ሊሊ \"አባዬ እንዴት ኮከብ መያዝ እችላለሁ?\" ብላ ጠየቀችው።\n\nአባቷ ፈገግ እያለ \"ሊሊዬ ኮከቦች በጣም ከፍ ይላሉ ስለዚህ በፍጹም ልንይዛቸው አንችልም። ምናልባት በህልማችን ልንይዛቸው እንችል ይሆናል።\" አላት።\n\nአንድ ምሽት ግን ሊሊ ለመሞከር ወሰነች። ወደ ወንዙ ወጥታ ኮከቦችን በትኩረት አየቻቸው። ከፍ ወዳለ ጉብታ ላይም ወጣች።\n\nከዋክብት ግን ሩቅ ነበሩ። ሊሊ ከዋክብቱን መያዝ ባለመቻላች አዝና ወደ አባቷ ሄደች።\n\nአባቷ ሲያያት አይኖቿ በእንባ ተሞልተዋል። አባቷ ሀዘኗን ለማጥፋት እጇን ይዞ ወደ ወንዙ ወሰዳት።\n\nከጨረቃ ብርሃን ስር የሚያንፀባርቀውን ወንዝ እየጠቆመ \"ሊሊ ከዋክብት በውሃ ውስጥ ተንፀባርቀዋል። እነሱን ለመንካት ቅርብና ቀላል ነው።\" አለ።\n\nሊሊ አየች እና እዚያው ወንዙ ላይ የሚያብረቀርቁ የከዋክብት ነፀብራቅ ነበሩ። እሷም ወደ ወንዙ ቀረብ ብላ ውሃውን ዳሰሰች።\n\nእጆቿን የውሃው ቅዝቃዜ ተሰማት። ትክክለኛው ኮከብ አልነበረም ግን በቂ ነበር።\n\nአባቷም \"እይ ሊሊ አንዳንድ ጊዜ በጣም የምንወዳቸው ነገሮች እኛ እንደምንይዛቸው ሆነው ቅርብ ናቸው። እና አንዳንድ ጊዜ ደሞ ልንይዛቸው የምንችላቸው አይደሉም።\" አለ።\n\nሊሊ አባቷ ያላትን በደንብ ተረድታለች። አባቷን እቅፍ አደረገች ልቧም በደስታ ተሞልቶ ነበር።\n\nየወላጆቿ ፍቅር እና የሌሊት ሰማይ ውበት በቂ መሆኑን ተገነዘበች።\n\n\"አስማተኛ እንደሆንኩ እንዲሰማኝ ኮከብ መያዝ ማሰብ አልነበረኝም የማይሆን ነው።\" አለች።\n\nትምህርቱ፡ የማይቻልን ለመያዝ መጣር ሳያስፈልግ የምንችለውን ማድረግ ይኖርብናል።",
            "Once upon a time, Lili and her parents lived in a small house by a beautiful river. Every evening, Lili and her father would sit by the riverbank admiring the stars. One evening, Lili asked her father \"How can I catch a star?\" Her father smiled and said \"Stars are very high, so we can never catch them. But perhaps in our dreams, we might catch them.\" One night, Lili decided to try. She went to the river and looked at the stars. She climbed a high hill, but the stars were still far away. Sad that she couldn't catch the stars, she went to her father. Her father saw her teary eyes and took her to the river. Under the moonlight, he showed her the stars reflected in the water. \"Look Lili, the stars are reflected in the water. They are close and easy to touch.\" Lili saw the shimmering star reflections and touched the water. Her hands felt the cold water. It wasn't a real star, but it was enough. Her father said \"You see Lili, sometimes the things we love most are closer than we think, even if we can't hold them.\" Lili understood her father well. She hugged her father and her heart was filled with joy. She realized that her parents' love and the beauty of the night sky were enough.\n\nLesson: We should focus on what we can do instead of chasing the impossible.",
            "ሕዝባዊ ተረት", LiteratureCategory.TERET),
        LiteratureItem(11,"ንግሥት ማክዳና የጥበብ ፍለጋ","Queen Makeda and the Quest for Wisdom",
            "ከብዙ ሺህ ዓመታት በፊት በኢትዮጵያ ምድር ንግሥት ማክዳ (የሳባ ንግሥት) የምትባል ብልህና ቆንጆ ንግሥት ነበረች። ንግሥት ማክዳ ሀገሯን በፍትህ የምታስተዳድር ቢሆንም፣ ሁልጊዜም ለዕውቀትና ለጥበብ ትልቅ ጥማት ነበራት።\n\nበዚያ ዘመን በእስራኤል ሀገር ንጉሥ ሰለሞን የተባለ እጅግ ጥበበኛ ንጉሥ እንዳለ ወሬው ደረሳት። ንግሥቲቱም \"በዓይን አይቼ ካላረጋገጥኩ አላምንም\" በማለት፣ ብዙ ወርቅና ሽቶዎችን ጭና ረጅሙን መንገድ ተጉዛ ኢየሩሳሌም ደረሰች።\n\nንግሥት ማክዳ የንጉሡን ጥበብ ለመፈተን ብዙ የሚከብዱ ጥያቄዎችን አዘጋጅታ ነበር።\n\nከፈተናዎቹ አንዱ እንዲህ ነበር፦\n\nየሰው ሰራሽና የተፈጥሮ አበባዎች ፈተና\nንግሥቲቱ ሁለት እኩል የሚያምሩ የአበባ ዘንጎችን አቀረበች። አንደኛው ከወርቅና ከሐር የተሰራ ሰው ሰራሽ አበባ ሲሆን፣ ሁለተኛው ደግሞ እውነተኛ የተፈጥሮ አበባ ነበር። ሁለቱም አንድ አይነት ስለሚመስሉ በዓይን መለየት አይቻልም ነበር።\n\nንጉሡን \"ሳይነኩት የትኛው እውነተኛ አበባ እንደሆነ ንገረኝ\" አለችው።\n\nንጉሥ ሰለሞንም ፈገግ አለና መስኮቱ እንዲከፈት አዘዘ። ብዙም ሳይቆይ ንቦች ወደ ክፍሉ ገብተው በአንዱ አበባ ላይ ብቻ አረፉ።\n\nንጉሡም \"ንቦች ያረፉበት እውነተኛው አበባ ነው\" ብሎ መለሰላት። ንግሥቲቱም በጥበቡ ተገረመች።\n\nትምህርቱ፡ ዕውቀትና ጥበብ ከወርቅና ከብር በላይ ዋጋ አላቸው። ለጥያቄዎችህ መልስ ለማግኘት ጥረትና ጉዞ ማድረግ አስፈላጊ ነው።",
            "Thousands of years ago, Queen Makeda (the Queen of Sheba) ruled Ethiopia. She was wise and beautiful. Though she ruled her country with justice, she always had a great thirst for knowledge and wisdom. When she heard about King Solomon in Israel, who was extremely wise, she said \"I won't believe until I see with my own eyes.\" She loaded many gold and gifts and traveled the long journey to Jerusalem.\n\nQueen Makeda prepared difficult questions to test the king's wisdom. One of her challenges was this: She presented two flower stems that looked equally beautiful. One was an artificial flower made of gold and silk, the other was a real natural flower. They looked so similar that it was impossible to tell them apart by eye. She asked the king \"Without touching them, tell me which is the real flower.\"\n\nKing Solomon smiled and ordered the window to be opened. Soon, bees flew into the room and landed on only one of the flowers. The king said \"The one the bees land on is the real flower.\" The queen was amazed by his wisdom.\n\nLesson: Knowledge and wisdom are more valuable than gold and silver. Making effort and traveling to find answers to your questions is important.",
            "ሕዝባዊ ተረት", LiteratureCategory.TERET),
        LiteratureItem(12,"አንበሳና አይጥ","The Lion and the Mouse",
            "ከዘመናት በፊት አንድ ታላቅ አንበሳ በጫካ ውስጥ ይኖር ነበር። አንድ ቀን ትንሽ አይጥ ሲጫወት አንበሳው ላይ ወደቀ። አንበሳው ተናደደ።\n\n\"ትንሽ አይጥ! ልበላህ!\" አለ አንበሳው።\n\n\"እባክህ ይቅር በለኝ!\" አለ አይጡ። \"አንድ ቀን ልረዳህ እችላለሁ።\"\n\nአንበሳው ሳቀ። ግን ይቅር አለው። ከጥቂት ቀናት በኋላ አንበሳው በአዳኞች ወጥመድ ተያዘ። አይጡ ሰምቶ ሮጠ። ገመዱን ቆረጠ። አንበሳው ነፃ ወጣ።\n\nትምህርቱ፡ ትንሽ ቢሆን ደግነት ትልቅ ዋጋ አለው።",
            "Long ago, a great lion lived in the forest. One day, a small mouse fell on the lion while playing. The lion was angry.\n\n\"Little mouse! I will eat you!\"\n\n\"Please forgive me! One day I can help you.\"\n\nThe lion laughed but let it go. Days later the lion was caught in a trap. The mouse chewed through the rope and freed him.\n\nLesson: Even small acts of kindness have great value.",
            "ሕዝባዊ ተረት", LiteratureCategory.TERET),
        LiteratureItem(11,"ሁለት ወንድሞች","Two Brothers",
            "ሁለት ወንድሞች አብረው ይኖሩ ነበር። አንደኛው ሀብታም ሌላኛው ደሃ ነበር። ሀብታሙ ወንድም ሁሌ ለራሱ ብቻ ያስብ ነበር።\n\nአንድ ቀን ደሃው ወንድም ታመመ። ሀብታሙ ወንድም ሊረዳው አልፈለገም። ጎረቤቶቹ ግን ደሃውን ወንድም ረዱ።\n\nደሃው ወንድም ዳነ። ሀብታሙ ወንድም ተፀፀተ።\n\nትምህርቱ፡ ቤተሰብ ከሁሉ ይበልጣል።",
            "Two brothers lived together. One was rich, the other poor. The rich brother always thought only of himself.\n\nOne day the poor brother fell ill. The rich brother refused to help. But the neighbors helped.\n\nThe poor brother recovered. The rich brother felt remorse.\n\nLesson: Family is above all.",
            "ሕዝባዊ ተረት", LiteratureCategory.TERET),
        LiteratureItem(12,"ቀበሮና ወፍ","The Fox and the Bird",
            "አንድ ቀበሮ ዛፍ ላይ ሥጋ ይዛ ቁጭ ያለች ወፍ አየ። ሥጋዋን ሊወስድ ፈለገ።\n\n\"ወፍ ሆይ! ድምፅሽ ምን ያህል ቆንጆ ነው! ዘምሪልኝ!\" አለ ቀበሮው።\n\nወፉ ደስ ብሏት ዘምራ ሥጋዋ ወደቀ። ቀበሮው ሥጋዋን ወሰደ።\n\nትምህርቱ፡ ሽሙጥ ሲሰሙ ጥንቃቄ አድርጉ።",
            "A fox saw a bird in a tree holding meat. He wanted to take it.\n\n\"O bird! How beautiful your voice is! Sing for me!\"\n\nThe bird was pleased and sang, dropping her meat. The fox took it.\n\nLesson: Be careful when you hear flattery.",
            "ሕዝባዊ ተረት", LiteratureCategory.TERET)
    )

    val misaleoch = listOf(
        LiteratureItem(20,"ዕውቀት ሀብት ነው","Knowledge is Wealth","ዕውቀት ሀብት ነው ማንም ሊወስደው አይችልም።","Knowledge is wealth that no one can take away.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(21,"አንድ ለሁሉ ሁሉ ለአንድ","One for All, All for One","አንድ ለሁሉ ሁሉ ለአንድ — ይህ ነው የኢትዮጵያ መንፈስ።","One for all, all for one — this is the spirit of Ethiopia.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(22,"ዛፍ ሲያድግ ፍሬ ይሰጣል","A Growing Tree Bears Fruit","ዛፍ ሲያድግ ፍሬ ይሰጣል — ሰው ሲማር ሀብት ያፈራል።","A growing tree bears fruit — a learning person creates wealth.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(23,"ቸኩሎ ሲሄድ ዘግይቶ ይደርሳል","Haste Makes Waste","ቸኩሎ ሲሄድ ዘግይቶ ይደርሳል።","One who rushes arrives late.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(24,"ፍቅር ሁሉን ያሸንፋል","Love Conquers All","ፍቅር ሁሉን ያሸንፋል — ጦርነትም ቢሆን።","Love conquers all — even war.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(25,"ዝምታ ወርቅ ነው","Silence is Gold","ዝምታ ወርቅ ነው — ቃል ብር ነው።","Silence is gold — words are silver.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(26,"ጤና ሀብት ነው","Health is Wealth","ጤና ሀብት ነው — ሀብት ጤና አይደለም።","Health is wealth — wealth is not health.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(27,"ትዕግስት ፍሬ ያፈራል","Patience Bears Fruit","ትዕግስት ፍሬ ያፈራል — ቸኩሎ ሲሄድ ይወድቃል።","Patience bears fruit — the hasty one falls.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(28,"ሀብት የማይቀረው ሰው የለም","No One Who Doesn't Lack","ሀብት የማይቀረው ሰው የለም — ሁሉም የሚያጣው ነገር አለው።","There is no one who doesn't lack something — everyone has something they miss.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(29,"የሌሊት ህልም ቀን ይሆናል","Night Dream Becomes Day","የሌሊት ህልም ቀን ይሆናል — ከጥረት ጋር።","A night dream becomes day — with effort.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(30,"ጓደኛ እራት ያውቃል","A Friend Knows Dinner","ጓደኛ እራት ያውቃል — ግን ጠጁን አያውቅም።","A friend knows dinner — but doesn't know the cost.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(31,"አይጥ በደመና ላይ ወጥቶ ድብን ይዞ ይዞራል","Mouse on Cloud","አይጥ በደመና ላይ ወጥቶ ድብን ይዞ ይዞራል — ድርቅ ባለ ጊዜ።","A mouse on a cloud taking a bear around — during drought.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(32,"እንጀራ መብላት ቀላል ነው","Eating Injera is Easy","እንጀራ መብላት ቀላል ነው — ማብሰል ግን ከባድ።","Eating injera is easy — but cooking it is hard.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(33,"አምላክ ይኖራል ምግብ ይሰጣል","God Exists He Gives Food","አምላክ ይኖራል ምግብ ይሰጣል — ለሚሰራ።","God exists, He gives food — to those who work.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(34,"የተፈረሰ ቤት ይከራቸዋል","A Collapsed House is Haunted","የተፈረሰ ቤት ይከራቸዋል — እውቀት ያለው ሰው ይጠብቃል።","A collapsed house is haunted — a knowledgeable person guards it.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(35,"ጥሩ ስም ከሀብት ይሻላል","Good Name Better Than Wealth","ጥሩ ስም ከሀብት ይሻላል — የሚጠፋው አይደለም።","A good name is better than wealth — it doesn't perish.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(36,"የሚሻውን ያገኛል","One Who Seeks Finds","የሚሻውን ያገኛል — ጥረት ያስፈራል።","One who seeks finds — effort bears fruit.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(37,"ልብህን ለወዳጅህ ስጥ","Give Your Heart to Your Friend","ልብህን ለወዳጅህ ስጥ — ግን አትስጠው።","Give your heart to your friend — but don't give it away.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(38,"ወንዝ ዳር የለው ጉድጓድ ውስጥ ይሞታል","River Without Bank Dies in Hole","ወንዝ ዳር የለው ጉድጓድ ውስጥ ይሞታል — ድንበር ያስፈልጋል።","A river without banks dies in a hole — boundaries are needed.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(39,"አንድ እጅ ድምፅ አያወጣም","One Hand Makes No Sound","አንድ እጅ ድምፅ አያወጣም — ሁለት እጅ ድምፅ ያወጣል።","One hand makes no sound — two hands make sound.","ሕዝባዊ",LiteratureCategory.MISALE),
        LiteratureItem(40,"ልጅ ሳይማር ያድጋል","A Child Grows Without Learning","ልጅ ሳይማር ያድጋል — ግን ሳይማር አይረዳም።","A child grows without learning — but without learning, they don't understand.","ሕዝባዊ",LiteratureCategory.MISALE)
    )

    val novels = listOf(
        LiteratureItem(30,"ፍቅር እስከ መቃብር","Fiqir Eske Mekabir",
            "ፍቅር እስከ መቃብር — ይህ ታዋቂ ልቦለድ በሀዲስ አለማየሁ የተጻፈ ሲሆን የሁለት ወጣቶች ፍቅር ታሪክ ነው። ቤዛ እና ሰይፉ ተፋቅረው ነበር ነገር ግን ማህበረሰቡ ፍቅራቸውን ለመቀበል ፈቃደኛ አልነበረም...\n\nይህ ልቦለድ ስለ ፍቅር፣ ስለ ማህበረሰብ ጫና፣ እና ስለ ሰብዓዊ ስሜቶች ጥልቅ ታሪክ ይናገራል።",
            "Fiqir Eske Mekabir (Love unto Crypt) — This famous novel by Haddis Alemayehu tells the story of two young lovers, Beza and Seyfu, whose love is challenged by society.\n\nThis novel speaks deeply about love, social pressure, and human emotions.",
            "ሀዲስ አለማየሁ", LiteratureCategory.NOVEL),
        LiteratureItem(31,"አደፍርስ","Adefris",
            "አደፍርስ — በዳኛቸው ወርቁ የተጻፈ ይህ ልቦለድ ስለ ኢትዮጵያ ማህበረሰብ ለውጥ እና ስለ ወጣቶች ሕይወት ይናገራል።\n\nዋናው ገጸ ባህሪ አደፍርስ ሕይወቱን ለማሻሻል ሲታገል ብዙ ፈተናዎችን ያልፋል።",
            "Adefris — Written by Dagnachew Worku, this novel speaks about social change in Ethiopia and the lives of young people.\n\nThe main character Adefris faces many challenges while striving to improve his life.",
            "ዳኛቸው ወርቁ", LiteratureCategory.NOVEL),
        LiteratureItem(32,"ከአድማስ ባሻገር","Beyond the Horizon",
            "ከአድማስ ባሻገር — ይህ ልቦለድ ስለ ኢትዮጵያ ታሪክ እና ስለ ሕዝቦቿ ትግል ይናገራል። ዋናው ገጸ ባህሪ ሀገሩን ለማሻሻል ሲታገል ብዙ ፈተናዎችን ያልፋል።",
            "Beyond the Horizon — This novel speaks about Ethiopian history and the struggles of its people. The main character faces many challenges while fighting to improve his country.",
            "ሕዝባዊ", LiteratureCategory.NOVEL)
    )

    val podcasts = listOf(
        PodcastItem(1,"የአማርኛ ሥነ ጽሑፍ ፖድካስት","Amharic Literature Podcast",
            "Ethiopian Literature Channel","https://www.youtube.com/results?search_query=amharic+literature+podcast","🎙️",
            "ስለ ኢትዮጵያ ሥነ ጽሑፍ የሚወያዩ ፖድካስቶች"),
        PodcastItem(2,"ግጥም ዓለም","Poetry World",
            "Amharic Poetry","https://www.youtube.com/results?search_query=amharic+poetry","📜",
            "የአማርኛ ግጥሞች እና ትንታኔዎቻቸው"),
        PodcastItem(3,"ተረት ዓለም","Folktale World",
            "Ethiopian Stories","https://www.youtube.com/results?search_query=ethiopian+folktales+amharic","🦁",
            "የኢትዮጵያ ተረቶች እና ታሪኮች"),
        PodcastItem(4,"ሥነ ጽሑፍ ትምህርት","Literature Lessons",
            "Learn Amharic Literature","https://www.youtube.com/results?search_query=learn+amharic+literature","📚",
            "ስለ አማርኛ ሥነ ጽሑፍ ትምህርቶች"),
        PodcastItem(5,"ደራሲዎች ዓለም","Authors World",
            "Ethiopian Authors","https://www.youtube.com/results?search_query=ethiopian+authors+interview","✍️",
            "ታዋቂ ኢትዮጵያዊ ደራሲዎች ቃለ ምልልስ")
    )

    val quizQuestions = listOf(
        QuizQuestion(1,"የኢትዮጵያ ዋና ከተማ የትኛው ነው?","What is the capital city of Ethiopia?",
            listOf("አዲስ አበባ","ባህር ዳር","ሐዋሳ","ድሬዳዋ"),0,
            "አዲስ አበባ የኢትዮጵያ ዋና ከተማ ነው።","Addis Ababa is the capital city of Ethiopia."),
        QuizQuestion(2,"ኢትዮጵያ ስንት ክልሎች አሏት?","How many regions does Ethiopia have?",
            listOf("10","11","12","14"),3,
            "ኢትዮጵያ 14 ክልሎች አሏት።","Ethiopia has 14 regions."),
        QuizQuestion(3,"የአድዋ ጦርነት የተካሄደው መቼ ነው?","When did the Battle of Adwa take place?",
            listOf("1896","1900","1889","1910"),0,
            "የአድዋ ጦርነት በ1896 ዓ.ም ተካሄደ።","The Battle of Adwa took place in 1896."),
        QuizQuestion(4,"ቡና የተገኘው ከየት ነው?","Where was coffee discovered?",
            listOf("ብራዚል","ኢትዮጵያ","ኮሎምቢያ","ቬትናም"),1,
            "ቡና ለመጀመሪያ ጊዜ የተገኘው ኢትዮጵያ ውስጥ ነው።","Coffee was first discovered in Ethiopia."),
        QuizQuestion(5,"ሉሲ (ዲንቅነሽ) ምን ዓይነት ፍጡር ነበረች?","What kind of creature was Lucy (Dinknesh)?",
            listOf("ዳይኖሰር","ቀደምት ሰው","ዝንጀሮ","ዓሣ"),1,
            "ሉሲ (ዲንቅነሽ) ቀደምት ሰው ነበረች።","Lucy (Dinknesh) was an early human ancestor."),
        QuizQuestion(6,"የኢትዮጵያ ብሔራዊ ቋንቋ ምንድን ነው?","What is the national language of Ethiopia?",
            listOf("ኦሮምኛ","ትግርኛ","አማርኛ","ሶማልኛ"),2,
            "አማርኛ የኢትዮጵያ ብሔራዊ ቋንቋ ነው።","Amharic is the national language of Ethiopia."),
        QuizQuestion(7,"ጥቁር አባይ ወይም ዓባይ ወንዝ ከየት ይወጣል?","Where does the Blue Nile originate?",
            listOf("ሐይቅ ጣና","ሐይቅ ዚዋይ","ሐይቅ ቱርካና","ሐይቅ ሻላ"),0,
            "ዓባይ ወንዝ ከሐይቅ ጣና ይወጣል።","The Blue Nile originates from Lake Tana."),
        QuizQuestion(8,"ፀጋዬ ገብረ መድህን ምን ዓይነት ጸሐፊ ነበሩ?","What kind of writer was Tsegaye Gebre-Medhin?",
            listOf("ልቦለድ ጸሐፊ","ገጣሚ እና ተውኔት ጸሐፊ","ታሪክ ጸሐፊ","ጋዜጠኛ"),1,
            "ፀጋዬ ገብረ መድህን ታዋቂ ኢትዮጵያዊ ገጣሚ እና ተውኔት ጸሐፊ ነበሩ።","Tsegaye Gebre-Medhin was a renowned Ethiopian poet and playwright."),
        QuizQuestion(9,"ቀዳማዊ ኃይለ ሥላሴ ስልጣን የያዙት መቼ ነው?","When did Emperor Haile Selassie come to power?",
            listOf("1930","1935","1941","1955"),0,
            "ቀዳማዊ ኃይለ ሥላሴ በ1930 ዓ.ም ስልጣን ያዙ።","Emperor Haile Selassie came to power in 1930."),
        QuizQuestion(10,"ኢትዮጵያ ስንት ዓመት ታሪክ አላት?","How many years of history does Ethiopia have?",
            listOf("1000 ዓመት","2000 ዓመት","3000 ዓመት","3000+ ዓመት"),3,
            "ኢትዮጵያ ከ3000 ዓመት በላይ ታሪክ አላት።","Ethiopia has more than 3000 years of history.")
    )

    val quotes = listOf(
        QuoteItem(1,"ዕውቀት ሀብት ነው ማንም ሊወስደው አይችልም።","Knowledge is wealth that no one can take away.","ሕዝባዊ", listOf(0xFF1A0A2E,0xFF16213E)),
        QuoteItem(2,"ፍቅር ሁሉን ያሸንፋል።","Love conquers all.","ሕዝባዊ", listOf(0xFF8B0000,0xFF4A0000)),
        QuoteItem(3,"ትምህርት ብርሃን ነው ጨለማን ያስወግዳል።","Education is light that removes darkness.","ሕዝባዊ", listOf(0xFF0D3B2E,0xFF1A5C45)),
        QuoteItem(4,"ትዕግስት ፍሬ ያፈራል።","Patience bears fruit.","ሕዝባዊ", listOf(0xFF2C1810,0xFF5C3317)),
        QuoteItem(5,"ኢትዮጵያ ለዘላለም ትኑር።","May Ethiopia live forever.","ሕዝባዊ", listOf(0xFF006400,0xFF8B0000)),
        QuoteItem(6,"አንብቦ ያደገ ሰው ዓለምን ያሸንፋል።","One who grows through reading conquers the world.","ሕዝባዊ", listOf(0xFF1A1A2E,0xFF16213E)),
        QuoteItem(7,"ዛሬ ያልሠራህ ነገ ያዝናናል።","What you don't do today will grieve you tomorrow.","ሕዝባዊ", listOf(0xFF2D1B69,0xFF11998E)),
        QuoteItem(8,"ቤተሰብ ከሁሉ ይበልጣል።","Family is above all.","ሕዝባዊ", listOf(0xFF4A0E8F,0xFF9B59B6))
    )
}
