document.querySelector('form').addEventListener('submit', async (e) => {
  e.preventDefault();

  const username = document.querySelector('#username').value;
  const password = document.querySelector('#password').value;

  const response = await fetch('http://localhost:8080/api/users/login', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password })
  });

  if (response.ok) {
      alert('Login successful!');
      const userDetails = await response.json();
      console.log(userDetails)
      sessionStorage.setItem('userDetails', JSON.stringify(userDetails));
      window.location.href = 'dash.html';
  } else {
      alert('Login failed! Please check your username and password.');
  }
});
