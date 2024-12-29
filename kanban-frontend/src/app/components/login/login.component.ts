import { Component } from '@angular/core';
import { NgForm } from "@angular/forms";
import { Router } from "@angular/router";
import { LoginService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  authToken!: string;
  successMessage: string = '';
  errorMessage: string = '';  // Error message for login
  signupErrorMessage: string = ''; // Error message for signup

  constructor(private authService: LoginService, private router: Router) {}

  onLoginSubmit(form: NgForm) {
    const { username, password } = form.value;

    // Check for empty fields
    if (!username || !password) {
      this.errorMessage = 'All fields are required.';
      return;
    }

    this.authService.authenticate(username, password).subscribe(
      response => {
        if (response) {
          this.successMessage = "Logged in successfully!";
          this.errorMessage = '';
          this.router.navigate(['/boards']);
        }
      },
      error => {
        this.errorMessage = "Username or password incorrect. Please try again.";  // Set error message for login
        this.successMessage = '';
      }
    );
    form.reset();
  }

  onSignupSubmit(form: NgForm) {
    const { username, email, password } = form.value;

    // Log the content of the email field
    console.log("Email entered:", email);

    // Check for empty fields
    if (username == "" || email == "" || password == "") {
      this.signupErrorMessage = "All fields are required.";
      return;
    }

    this.authService.register(username, email, password).subscribe(
      response => {
        if (response) {
          this.signupErrorMessage = ''; // Clear the error message on success
          alert("Your account has been created successfully.");
          this.router.navigate(['/home']);
        }
      },
      error => {
        this.signupErrorMessage = "Username or email already exists. Please try again."; // Set the error message for signup
      }
    );
    form.reset();
  }
}
