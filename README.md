<a href="https://drive.google.com/file/d/1nFcFCX3Kb8aXnC7IZapeqOLHwLZaVYOl/view?usp=sharing"># andriod_final</a>

Technical specifications for final project
Description: This application is a prototype for a diploma project named “HeyGo”. The purpose of this application is to create a system in which a tourist can find a cheap of free guide that can show a tour in a city and tell detailed information about city. Application will give a choice to guide or to go. If you push “guide” you can see a list of people that want to visit your city and you can start chat with them. Or if you push “go” you can choose city and see a list of guides that can guide you.
Technologies:
1. Shared Preferences
For storing information locally
2. Firebase Authentication
For registration and login using firebase.
3. Firebase Firestore
For storing all information needed to be contained in the database
4. Retrofit
Retrieving information for web API
5. Picasso
For working with pictures
Functionality
Final version of an application will be capable of performing such functionality:
1. Registration and login
First of all, if the user does not have an account in the application, he/she has to pass registration first. It includes the standard process of filling name, surname, age, and so forth. The account password will be automatically validated by Firebase Auth, so it will more complex. For account verification, users can choose two options: send a message to their email or send an SMS message to the phone number. After successful registration, user can log in the application using email and password.
2. Main menu
After logging in user will see two big buttons “go, guide” and some minor buttons like profile and back.
3.1 Search for guides
If user wants to travel to another city, he can push go and type city name. After that user will see a list of guides and can interact with them. User can start a chat with guide and start a meeting.
3.2 Search for tourists
If user wants to be a guide he pushes “guide” button and can see a list of users who wants to visit this city. Guide can also interact with tourist and initiate meeting with user via chat.
4. User profile
Here users can see and modify all information they entered in the registration.

Note: Additional functionality will be added for both guide and tourist.

