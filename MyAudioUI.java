import java.util.ArrayList;
import java.util.Scanner;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			try{
				String action = scanner.nextLine();

				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;
				
				else if (action.equalsIgnoreCase("STORE"))	// List all songs
				{
					store.listAll(); 
				}
				else if (action.equalsIgnoreCase("SONGS"))	// List all songs
				{
					mylibrary.listAllSongs(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
				{
					mylibrary.listAllAudioBooks(); 
				}
				else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
				{
					mylibrary.listAllArtists(); 
				}
				else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
				{
					mylibrary.listAllPlaylists(); 
				}
				// Download audiocontent (song/audiobook/podcast) from the store 
				// Specify the index of the content
				else if (action.equalsIgnoreCase("DOWNLOAD")) 
				{
					//variable initialization
					int fromIndex = 0;
					int toIndex = 0;

					System.out.print("From Store Content #: ");
					if (scanner.hasNextInt())
					{
						fromIndex = scanner.nextInt();     //from beginning of range
					}

					System.out.print("To Store Content #: ");
					if (scanner.hasNextInt())
					{
						toIndex = scanner.nextInt();       //to end of range
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					
					for(int i = fromIndex; i <= toIndex; i++){         //iterate through the range
						AudioContent content = store.getContent(i);    //retrieves content
						try{
							mylibrary.download(content);               //download content
						}
						catch(RuntimeException exception){              //if content not found
							System.out.println(exception.getMessage()); //throws exception
						}
					}
				}
				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song 
				else if (action.equalsIgnoreCase("PLAYSONG")) 
				{
					int index = 0;                           //initializing variable
				
					System.out.print("Song Number: ");     //prompt
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();           //user input
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
						mylibrary.playSong(index);   // calling the playSong method 
						System.out.println(mylibrary.getErrorMessage());   //prints error message if the song doesn't exist in the library
				// Print error message if the song doesn't exist in the library
			
				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
					// Print error message if the book doesn't exist in the library

					int index = 0;  //initializing variable
				
					System.out.print("Audio Book Number: ");     //prompt
					if (scanner.hasNextInt()){
						index = scanner.nextInt();                 //user input
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
						mylibrary.printAudioBookTOC(index);           // calling the printAudioBookTOC method 
				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter 
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{
					//variable initialization
					int index = 0;
					int chapNum = 0;
				
					System.out.print("Audio Book Number: ");   //prompt
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();         //user input book index
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					System.out.print("Chapter: ");               //prompt
					if (scanner.hasNextInt())
					{
						chapNum = scanner.nextInt();        //user input chapter number
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
						mylibrary.playAudioBook(index, chapNum);            // calling the playAudioBook method 					
				}
				// Specify a playlist title (string) 
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{
					String title = "";       //variable initialization
				 
					System.out.print("Playlist Title: ");   //prompt
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();           //user input
					}
					mylibrary.playPlaylist(title);            // calling the playPlaylist method 
				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				// Play all the audio content 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					//variable initialization
					String title = "";
					int contentNum = 0;
				
					System.out.print("Playlist Title: ");           //prompt
					if (scanner.hasNext())
					{
						title = scanner.next();                       //user input -- > title
					}
					System.out.print("Content Number: ");           //prompt
					if (scanner.hasNextInt())
					{
						contentNum = scanner.nextInt();               //user input -- > content number
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					System.out.println(title);                        //printing title of playlist
					mylibrary.playPlaylist(title, contentNum);        // calling the playPlaylist method 
				
				}
				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					int index = 0;                                   //variable initialization
				
					System.out.print("Library Song #: ");          //prompt
					if (scanner.hasNextInt()){
						index = scanner.nextInt();                   //user input
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
						mylibrary.deleteSong(index);                 // calling the deleteSong method 
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					String title = "";          //variable initialization
				
					System.out.print("Playlist Title: ");          //prompt
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();                  //user input -- > title
					}
					mylibrary.makePlaylist(title);                   // calling the makePlaylist method
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
				{
					String title = "";      //variable initialization
				
					System.out.print("Playlist Title: ");    //prompt
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();            //user input --> title
					}
					mylibrary.printPlaylist(title);            // calling the printPlaylist method
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					//variable initialization
					String title = "";
					String type = "";
					int index = 0;
				
					System.out.print("Playlist Title: ");    //prompt
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();            //user input --> title
					}
					System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");    //prompt
					if (scanner.hasNextLine())
					{  
						type = scanner.nextLine();            //user input  --> type
					}
					System.out.print("Library Content #: ");      //prompt
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();           //user input --> index
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					mylibrary.addContentToPlaylist(type, index, title);       // calling the addContentToPlaylist method
				}
				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{
					//variable initialization
					String title = "";
					int index = 0;
				
					System.out.print("Playlist Title: ");    //prompt
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();            //user input --> title
					}
					System.out.print("Playlist Content #: ");    //prompt
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();             //user input  --> index
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					mylibrary.delContentFromPlaylist(index, title);  // calling the delContentFromPlaylist method
				}
				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}
				else if(action.equalsIgnoreCase("SEARCH"))
				{
					String title = "";      //variable initialization
				
					System.out.print("Title: ");    //prompt
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();            //user input --> title
					}
					store.search(title);           // calling the search method
				}

				else if(action.equalsIgnoreCase("SEARCHA"))
				{
					String artist = "";                                   //variable initialization
				
					System.out.print("Artist: ");                      //prompt
					if (scanner.hasNextLine())
					{
						artist = scanner.nextLine();                     //user input --> title
					}
					ArrayList<Integer> storeIndex = store.searchA(artist);    // calling the searchA method

					for(int i = 0; i < storeIndex.size(); i++){               //iterates through the store index arraylist
						System.out.print(storeIndex.get(i)+1 + ". ");         //prints the index number
						store.getContent(storeIndex.get(i)+1).printInfo();    //prints the content in that index
						System.out.println();                           //line space
					}
				}

				else if(action.equalsIgnoreCase("SEARCHG"))
				{
					//varaible declaration
					String genre = "";

					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");  //prompt

					if (scanner.hasNextLine())
					{  
						genre = scanner.nextLine();            //user input  --> type
					}
					ArrayList<Integer> storeIndex = store.searchG(Song.Genre.valueOf(genre));   // calling the searchG method

					for(int i = 0; i < storeIndex.size(); i++){                    //iterates through the store index arraylist
						System.out.print(storeIndex.get(i)+1 + ". ");              //prints the index number
						store.getContent(storeIndex.get(i)+1).printInfo();         //prints the content in that index
						System.out.println();                                //line space
					}
				}

				else if(action.equalsIgnoreCase("DOWNLOADA"))
				{
					String artist = "";                                      //variable declaration

					System.out.print("Artist Name: ");                     //prompt
					if (scanner.hasNextLine())
					{
						artist = scanner.nextLine();                         //user input --> title
					}
					ArrayList<Integer> storeList = store.searchA(artist);    //calling the searchA method in store and storing all content with the same artist
							
					for(int index : storeList){                              //iterating through storeList
						AudioContent content = store.getContent(index+1);    //retrieving content from the index in store
						try{
							mylibrary.downloadArtistAuthorGenre(content);                   //download content with the same artist         
						}
						catch(RuntimeException exception){                   //otherwise throw exception
							System.out.println(exception.getMessage());      //print error message
						}
					}
				}

				else if(action.equalsIgnoreCase("DOWNLOADG"))
				{
					String genre = "";                                      //variable declaration

					System.out.print("Genre: ");                     //prompt
					if (scanner.hasNextLine())
					{
						genre = scanner.nextLine();                         //user input --> title
					}
					ArrayList<Integer> storeList = store.searchG(Song.Genre.valueOf(genre));    //calling the searchG method in store and storing all content with the same genre
							
					for(int index : storeList){                             //iterating through storeList 
						AudioContent content = store.getContent(index+1);   //retrieving content from the index in store
						try{
							mylibrary.downloadArtistAuthorGenre(content);                  //download content with the same genre
						}
						catch(RuntimeException exception){                  //otherwise throw exception
							System.out.println(exception.getMessage());     //print error message
						}
					}
				}
			} 
			catch(RuntimeException exception){                              //catch RuntimeException
				System.out.println(exception.getMessage());                 //print error message
			}
			System.out.print("\n>");
		}
	}
}
