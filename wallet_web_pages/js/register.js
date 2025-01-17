document.querySelector('form').addEventListener('submit', async (e) => {
  e.preventDefault();

  const username = document.querySelector('#name').value;
  const email = document.querySelector('#email').value;
  const password = document.querySelector('#password').value;

  const response = await fetch('http://localhost:8080/api/users/register', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, email, password })
  });

  if (response.ok) {
      alert('Registration successful!');
      // Redirect to login page
      window.location.href = 'login.html';
  } else {
      const errorMessage = await response.text();
      alert(`Registration failed: ${errorMessage}`);
  }
});
