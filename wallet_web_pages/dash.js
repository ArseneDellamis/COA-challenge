// Fetch user details when the page loads
document.addEventListener('DOMContentLoaded', async () => {
  const usernameElement = document.querySelector('#username');
  const welcomeNameElement = document.querySelector('#welcome-name');

  try {
      let user = JSON.parse(sessionStorage.getItem('userDetails'))

      if (user) {
        console.log("user :", user)
        
          usernameElement.textContent = user.username; // Update "Logged in as"
          welcomeNameElement.textContent = user.username; // Update welcome message
      } else if (!user) {
          // If the session has expired or the user is unauthorized, redirect to login
          alert('Session expired. Please log in again.');
          window.location.href = 'login.html';
      } else {
          console.error('Error fetching user details: not logged in');
      }
  } catch (error) {
      console.error('Error:', error);
      alert('An error occurred while fetching user details.');
      window.location.href = 'login.html'; // Redirect to login on error
  }
});

// Logout functionality
document.querySelector('#logout-btn').addEventListener('click', async () => {
  try {// Clear session storage
    sessionStorage.removeItem('userDetails'); // Removes specific item
    // Or clear all session storage
    sessionStorage.clear(); 
      alert("you logged out")
    // Redirect to the login page
    window.location.href = 'login.html';
  
  } catch (error) {
      console.error('Error during logout:', error);
      alert('An error occurred while logging out.');
  }
});
