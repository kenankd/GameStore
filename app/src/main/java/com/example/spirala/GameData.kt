package com.example.spirala

class GameData {
    companion object{
         fun getAll() : List<Game> {
            return listOf(Game("Fifa 23","PS5","2022-27-09",4.1,"fifa23",
            "E","Jon Jones","EA Sports","sport","FIFA 23 features the men's World Cup game mode and is reported to feature the women's World Cup game mode in the future, replicating the 2022 FIFA World Cup and the 2023 FIFA Women's World Cup.",
                mutableListOf(UserRating("kdizdarevi1",1649182100000,3.5),UserReview("ali123",1670718100000,"This game is super fun!"),UserReview("aa",1643855700000,"Didn't have a good time playing this game, too many ads..."),UserRating("ja",1665391700000,4.5),UserRating("kenankd",1639012500000,1.0))
            ),Game("NBA 2K23","PS4","2022-09-01",4.4,"nba23",
                "E","Conor Mcgregor","2K Sports","sports","NBA 2K23 is a 2022 basketball video game developed by Visual Concepts and published by 2K Sports, based on the National Basketball Association (NBA).",
                mutableListOf(UserReview("faris123",1673290900000,"Amazing graphics and movement of players"),UserRating("JoshJJ",1653201300000,2.0),UserReview("JonJones",1662465300000,"Had a great experience with this game! I would recommend it to everyone!"),UserRating("TysonMike",1630511700000,2.5),UserRating("Tarik123",1684382100000,3.0))),
                Game("Call Of Duty Black Ops III","Xbox One","2014-03-03",4.8,"bo3",
                    "M","Anthony Joshua","Activision","shooting","Call of Duty: Black Ops III takes place in 2065, 40 years after the events of Black Ops II, in a world facing upheaval from conflicts, climate change and new technologies. ",
                    listOf()),
                Game("Rocket League","PC","2019-28-01",3.5,"rocketleague",
                    "E","Deontay Wilder","Psyonix","action game","Rocket League is a fantastical sport-based video game, developed by Psyonix (it's “soccer with cars”). It features a competitive game mode based on teamwork and outmaneuvering opponents.",
                    listOf()),
                Game("Pro Evolution Soccer 13","PS3","2012-28-09",5.0,"pes13",
                    "E","Mike Tyson","Konami","sports","Pro Evolution Soccer 2013 (PES 2013, known as World Soccer: Winning Eleven 2013 in Japan and South Korea) is an association football video game, developed and published by Konami.",
                    listOf()),
                Game("Fortnite","PS4","2014-01-01",4.5,"fortnite",
                    "C","Kenan Dizdarevic","Epic Games","battle royale","\"In Fortnite, players collaborate to survive in an open-world environment, by battling other characters who are controlled either by the game itself, or by other players.",
                    listOf()),
                Game("Chess","PC","2014-01-01",3.25,"chess",
                    "E+","kenan","Activision","sports","Chess.com is an internet chess server news website and social networking website.",
                    listOf()),
                Game("Rainbow Six Siege","PS4","2014-01-01",1.5,"sixsiege",
                    "M","kenan","ea","sports","football",listOf()),
                Game("League of Legends","PS4","2010-22-01",3.75,"lol",
                    "C","Tyson Fury","Riot","fantasy","League of Legends is one of the world's most popular video games, developed by Riot Games. It features a team-based competitive game mode based on strategy and outplaying opponents.",
                    listOf()),
                Game("Grand Theft Auto V","PS4","2014-01-01",4.9,"gta5",
                    "M","Muhamed Ali","Rockstar","action","Grand Theft Auto V is an action-adventure game played from either a third-person or first-person perspective.",
                    listOf()))
        }
        fun getDetails(title: String): Game? {
            val games: List<Game> = getAll()
            return games.find { games -> games.title == title }
        }
    }
}