package com.teamtreehouse.blog.MockDatabase;

import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import com.teamtreehouse.blog.model.BlogDAOImpl;

import java.util.*;

public class Database {
    public void initializeData(BlogDAOImpl dao){
        List<Comment> comments = new ArrayList<>();
        Set<String> tag1 = new HashSet<>();
        Set<String> tag2 = new HashSet<>();

        tag1.add("Vocations");
        tag1.add("Some Tag");
        tag2.add("hell");
        tag2.add("hell2");

        comments.add(new Comment("Vasya", "This is cool  man, i wish i could do the same at least once in my life"));
        comments.add(new Comment("Sara", "I don't believe you!!!"));
        dao.addEntry(new BlogEntry("The best day I’ve ever had",
                new Date(2016, Calendar.JANUARY, 31),
                "I'm supposed to say the day I got married or the birth of my children, but you didn't ask for an event, you asked for a day. So, the best day of my life happened in August 2013. For an entire day, I sat under a palm tree at the edge of Honolulu, looking out over the Pacific Ocean. Waves crashed below me. The wind danced in the palm fronds above me. I read \"Tattoos on the Heart: The Power of Boundless Compassion\" by Gregory Boyle. And my friend mixed me a Mai Tai whenever I wanted another one. When I have dark moments, I draw strength from remembering there are places like that in the world and moments like that in life.",
                comments, tag1));
        dao.addEntry(new BlogEntry("The absolute worst day I’ve ever had",
                new Date(2016, Calendar.JANUARY, 31),
                "Some text should be here, sorry had no time to look for materials..",
                new ArrayList<>(), tag1));
        dao.addEntry(new BlogEntry("That time at the mall",
                new Date(2016, Calendar.JANUARY, 31),
                "Some text should be here, sorry had no time to look for materials..",
                new ArrayList<>(), tag2));
        dao.addEntry(new BlogEntry("Dude, where’s my car?",
                new Date(2016, Calendar.JANUARY, 31),
                "Some text should be here, sorry had no time to look for materials..",
                new ArrayList<>(), tag2));
    }
}
