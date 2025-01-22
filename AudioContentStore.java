import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
	private ArrayList<AudioContent> contents; 
	private Map<String, Integer> titleMap;                             
	private Map<String, ArrayList<Integer> > artistOrAuthorMap;
	private Map<Song.Genre, ArrayList<Integer>> genreMap; 

	public AudioContentStore()
	{
		//map: variable initialization
		titleMap = new HashMap<String, Integer>();
		artistOrAuthorMap = new HashMap<String, ArrayList<Integer> >();
		genreMap = new HashMap<Song.Genre, ArrayList<Integer> >();

		//try-catch block
		try{
			contents = readContentsFromFile();      //calling readContentsFromFile method which reads the file store.txt
		}
		catch(IOException exception){                       //catches an exception when file is not found
			System.out.println(exception.getMessage());     //prints the error message
			System.exit(1);                          //program ends
		}

		// Update the title map
		for(int i = 0; i < contents.size(); i++){                      //iterates through all the content in contents arraylist
			titleMap.put(contents.get(i).getTitle(), i);               //searches store for the title and puts it into titleMap
		}

		// Update the artist map
		for(int i = 0; i < contents.size(); i++){                                     //iterates through all the content in contents arraylist
			ArrayList<Integer> indexVal = new ArrayList<Integer>();                   //initializes arraylist indexVal
			
			//mapping with artists
			if(contents.get(i).getType().equalsIgnoreCase("SONG")){     //checks if the content is song
				Song song = (Song)contents.get(i);                                    //creates object, retrieves song
				if(artistOrAuthorMap.containsKey(song.getArtist())){                          //checks if the artist is in artistMap
					indexVal = artistOrAuthorMap.get(song.getArtist());                       //gets the index value of song 
					indexVal.add(i);                                                  //puts the iterator value into the arraylist indexVal
				}
				else{                                                                 //otherwise
					indexVal.add(i);                                                  //puts the iterator value into the arraylist indexVal
					artistOrAuthorMap.put(song.getArtist(), indexVal);                        //puts artists into artistMap
				}
			}

			//mapping with authors
			if(contents.get(i).getType().equalsIgnoreCase("AUDIOBOOK")){//checks if the content is audiobook
				AudioBook audioBook = (AudioBook)contents.get(i);                     //creates object, retrieves audiobook
				if(artistOrAuthorMap.containsKey(audioBook.getAuthor())){                     //checks if the author is in artistMap
					indexVal = artistOrAuthorMap.get(audioBook.getAuthor());                  //gets the index value of audiobook
					indexVal.add(i);                                                  //puts the iterator value into the arraylist indexVal
				}
				else{                                                                 //otherwise
					indexVal.add(i);                                                  //puts the iterator value into the arraylist indexVal
					artistOrAuthorMap.put(audioBook.getAuthor(), indexVal);                   //puts authors into artistMap
				}
			}
		}

		// Update the genre map
		for(int i = 0; i < contents.size(); i++){
			ArrayList<Integer> indexVal = new ArrayList<Integer>();                        //initializes arraylist indexVal
			if(contents.get(i).getType().equalsIgnoreCase("SONG")){         //checks if the content is song
				Song song = (Song)contents.get(i);                                        //retrieves song
				for(Song.Genre genre: Song.Genre.values()){                               //iterates through genres of each content
					if(song.getGenre() == genre){                                         //checks if the retrieved song genre is equal to current genre
						if(genreMap.containsKey(genre)){                                  //checks if the genre is in genreMap
							indexVal = genreMap.get(genre);                               //gets the index value
							indexVal.add(i);                                              //puts the iterator value into the arraylist indexVal
						}
						else{                                        //otherwise
							indexVal.add(i);                         //puts the iterator value into the arraylist indexVal
							genreMap.put(genre, indexVal);           //updates genreMap
						}
					}
				}
			}
		}	
	}

	private ArrayList<AudioContent> readContentsFromFile() throws IOException{
		ArrayList<AudioContent> content = new ArrayList<AudioContent>();                //initializing content arraylist
		Scanner in = new Scanner(new File("store.txt"));                       //opening a scanner to reading

		while(in.hasNextLine()){                                            //while there is a next line to read
			String type = in.nextLine();                                    //initilizes the first line into variable type

			if(type.equalsIgnoreCase("SONG")){                //checks if type is song
				//variable declaration/initialization
				String id = in.nextLine();                                  //reads next line and stores it into variable id
				String title = in.nextLine();                               //reads next line and stores it into variable title
				int year = in.nextInt();                                    //reads next line and stores it into variable year (int)
				int length = in.nextInt();                                  //reads next line and stores it into variable length (int)
				in.nextLine();                                              //special scenario so that it does not show an error since prior line was an int
				String artist = in.nextLine();                              //reads next line and stores it into variable artist
				String composer = in.nextLine();                            //reads next line and stores it into variable composer
				Song.Genre genre = Song.Genre.valueOf(in.nextLine());       //reads next line and stores it into variable genre
				int numLines = in.nextInt();                                //reads next line and stores it into variable numLines
				in.nextLine();                                              //special scenario so that it does not show an error since prior line was an int
				
				String lyrics = "";                                         //variable declaration
				for(int i = 0; i < numLines; i++){                          //iterates through the lyrics
					lyrics += in.nextLine() + "\n";                         //adds the next lines to lyrics (sentences)
				}
				content.add(new Song(title, year, id, type, lyrics, length, artist, composer, genre, lyrics));  //calling method Song and adding to content (read file)
			}
			else if(type.equalsIgnoreCase("AUDIOBOOK")){     //checks if type is audiobook
				//variable declaration/initialization
				String id = in.nextLine();                                 //reads next line and stores it into variable id
				String title = in.nextLine();                              //reads next line and stores it into variable title
				int year = in.nextInt();                                   //reads next line and stores it into variable year (int)
				int length = in.nextInt();                                 //reads next line and stores it into variable length (int)
				in.nextLine();                                             //special scenario so that it does not show an error since prior line was an int
				String author = in.nextLine();                             //reads next line and stores it into variable author
				String narrator = in.nextLine();                           //reads next line and stores it into variable narrator
				int numChapters = in.nextInt();                            //reads next line and stores it into variable numChapter
				in.nextLine();                                             //special scenario so that it does not show an error since prior line was an int

				ArrayList<String> chapterTitles = new ArrayList<>();       //initializing arraylist chapterTitles
				ArrayList<String> chapters = new ArrayList<>();            //initializing arraylist chapters

				for(int i = 0; i < numChapters; i++){                      //iterates through num of chapters
					chapterTitles.add(in.nextLine());                      //adds the next line to chapterTitles (title)
				}

				for(int i = 0; i < numChapters; i++){                      //iterates through num of chapters
					int numLines = in.nextInt();                           //reads next line and stores it into variable numLines (int)
					in.nextLine();                                         //special scenario so that it does not show an error since prior line was an int
					
					String lines = "";                                     //variable declaration
					for(int j = 0; j < numLines; j++){                     //iterates through the lines
						lines += in.nextLine() + "\n";                     
					}
					chapters.add(lines);                                   //adds the next line to lines (sentences)
				}
				content.add(new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chapters));  //calling method Audiobook and adding to content (read file)
			}
		}
		return content;    //returns the completed file after reading
	}
	
	public AudioContent getContent(int index)
	{
		if (index < 1 || index > contents.size())
		{
			return null;
		}
		return contents.get(index-1);
	}

	//search by title mapping method
	public Map<String, Integer> searchByTitle() {
		return titleMap;
	}

	//search by artist/author mapping method
	public Map<String, ArrayList<Integer>> searchByArtistorAuthor() {
		return artistOrAuthorMap;
	}

	//search by genre mapping method
	public Map<Song.Genre, ArrayList<Integer>> searchByGenre() {
		return genreMap;
	}
		
	public void listAll()
	{
		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	//search method
	public void search(String title){                           //takes in title as parameter
		if(titleMap.containsKey(title)){                        //checks if titleMap has title
			for(String key : titleMap.keySet()){                //iterates through titleMap
				if(key.equals(title)){                          //checks if key equals given title
					int indexVal = titleMap.get(key);           //initializing index value 
					System.out.print(indexVal + 1 + ". ");      //printing out number (index)
					contents.get(indexVal).printInfo();         //printing out info of that content
				}
			}
		}
		else{
			throw new NoMatchesException("No matches for "+ title);   //otherwise throws exception, no title matches were found
		}
	}

	//searcha method
	public ArrayList<Integer> searchA(String artist){              //takes in artist/author as parameter
		ArrayList<Integer> artists = new ArrayList<>();            //initializing artists arraylist
		if(artistOrAuthorMap.containsKey(artist)){                 //checks if artistOrAuthorMap has given artist/author
			for(String key : artistOrAuthorMap.keySet()){          //iterates through artistOrAuthorMap
				if(key.equals(artist)){                            //checks if key equals given artist/author
					artists = artistOrAuthorMap.get(key);          //updates artists
				}
			}
		}
		else{
			throw new NoMatchesException("No matches for "+ artist);   //otherwise throws exception, no artist/author matches were found
		}
		return artists;                                            //returns artists arraylist
	}

	//searchg method
	public ArrayList<Integer> searchG(Song.Genre genre){            //takes in genre as parameter
		ArrayList<Integer> songGenre = new ArrayList<>();           //initializing songGenre arraylist
		if(genreMap.containsKey(genre)){                            //checks if genreMap has given genre
			for(Song.Genre key : genreMap.keySet()){                //iterates through genreMap
				if(key == genre){                                   //checks if key equals given genre
					songGenre = genreMap.get(key);                  //updates songGenre
				}
			}
		}
		else{
			throw new NoMatchesException("No matches for "+ genre); //otherwise throws exception, the given genre was not found
		}
		return songGenre;                                           //returns songGenre arraylist
	}

	//extends RunTimeException, checks to see if matches were found
	public class NoMatchesException extends RuntimeException{
		public NoMatchesException() {}

		public NoMatchesException(String message){
			super(message);
		}
	}
}
