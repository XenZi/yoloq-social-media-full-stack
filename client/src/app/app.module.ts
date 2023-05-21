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
import { PostComponent } from './components/post/post.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { ModalComponent } from './components/modal/modal.component';
import { UpdatePostFormComponent } from './forms/update-post-form/update-post-form.component';
import { DeletePostDialogComponent } from './dialogs/delete-post-dialog/delete-post-dialog.component';

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
    PostComponent,
    PostListComponent,
    ModalComponent,
    UpdatePostFormComponent,
    DeletePostDialogComponent,
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
