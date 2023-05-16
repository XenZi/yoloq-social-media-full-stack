import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { LoginFormComponent } from './forms/login-form/login-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from './components/button/button.component';
import { HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './pages/register/register.component';
import { RegisterFormComponent } from './forms/register-form/register-form.component';
import { HomeComponent } from './pages/home/home.component';
import { VerticalNavComponent } from './components/vertical-nav/vertical-nav.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { FriendRequestsListComponent } from './components/friend-requests-list/friend-requests-list.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoginFormComponent,
    ButtonComponent,
    RegisterComponent,
    RegisterFormComponent,
    HomeComponent,
    VerticalNavComponent,
    CreatePostComponent,
    FriendRequestsListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
