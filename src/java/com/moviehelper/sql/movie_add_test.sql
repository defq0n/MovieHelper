INSERT INTO "movie" ("title","description","poster_link","trailer_link","release_year","rating","actors","genre") VALUES

('MI-5','When a terrorist escapes custody during a routine handover, Will 
Holloway must team with disgraced MI5 Intelligence Chief Harry Pearce to track 
him down before an imminent terrorist attack on London.',
'http://ia.media-imdb.com/images/M/MV5BMjQyNzAxNjQwN15BMl5BanBnXkFtZTgwMTIyNTMwNzE@._V1_SX214_AL_.jpg',
'https://www.youtube.com/watch?v=Z2pVeuiKpCU','2015',0,
'Kit Harington, Peter Firth, Jennifer Ehle', 

(SELECT "genre"."id" FROM "genre" WHERE "genre"."title" = 'Action')

)
