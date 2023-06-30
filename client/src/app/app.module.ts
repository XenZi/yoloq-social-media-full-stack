import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { LoginFormComponent } from './forms/login-form/login-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from './components/button/button.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './pages/register/register.component';
import { RegisterFormComponent } from './forms/register-form/register-form.component';
import { HomeComponent } from './pages/home/home.component';
import { VerticalNavComponent } from './components/vertical-nav/vertical-nav.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { FriendRequestsListComponent } from './components/friend-requests-list/friend-requests-list.component';
import { PostComponent } from './components/post/post.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { UpdatePostFormComponent } from './forms/update-post-form/update-post-form.component';
import { DeletePostDialogComponent } from './dialogs/delete-post-dialog/delete-post-dialog.component';
import { CreateGroupFormComponent } from './forms/create-group-form/create-group-form.component';
import { GroupVerticalNavItemComponent } from './components/group-vertical-nav-item/group-vertical-nav-item.component';
import { UpdateGroupFormComponent } from './forms/update-group-form/update-group-form.component';
import { ChangePasswordFormComponent } from './forms/change-password-form/change-password-form.component';
import { ModalComponent } from './components/modal/modal.component';
import { ModalService } from './services/modal/modal.service';
import { ModalWrapperComponent } from './components/modal-wrapper/modal-wrapper.component';
import { DynamicHostDirective } from './directives/dynamic-host-directive/dynamic-host.directive';
import { OptionsMenuComponent } from './components/options-menu/options-menu.component';
import { ToastNotificationComponent } from './components/toast-notification/toast-notification.component';
import { ToastContainerComponent } from './components/toast-container/toast-container.component';
import { CreateCommentComponent } from './forms/create-comment/create-comment.component';
import { TokenInterceptor } from './interceptors/token/token.interceptor';
import { ShowPostWithCommentsComponent } from './components/show-post-with-comments/show-post-with-comments.component';
import { CommentComponent } from './components/comment/comment.component';
import { ReactionsComponent } from './components/reactions/reactions.component';
import { UpdateCommentFormComponent } from './forms/update-comment-form/update-comment-form.component';
import { DeleteCommentDialogComponent } from './dialogs/delete-comment-dialog/delete-comment-dialog.component';
import { ReplyComponent } from './components/reply/reply.component';
import { HeaderComponent } from './components/header/header.component';
import { NavComponent } from './components/nav/nav.component';
import { UserDropdownComponent } from './components/user-dropdown/user-dropdown.component';
import { GroupPageComponent } from './pages/group-page/group-page.component';
import { ShowUserInGroupComponent } from './components/show-user-in-group/show-user-in-group.component';
import { UserApproveGroupJoinComponent } from './components/user-approve-group-join/user-approve-group-join.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { SuspendGroupFormComponent } from './forms/suspend-group-form/suspend-group-form.component';
import { UpdateUserFormComponent } from './forms/update-user-form/update-user-form.component';
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
    UpdatePostFormComponent,
    DeletePostDialogComponent,
    CreateGroupFormComponent,
    GroupVerticalNavItemComponent,
    UpdateGroupFormComponent,
    ChangePasswordFormComponent,
    ModalComponent,
    ModalWrapperComponent,
    DynamicHostDirective,
    OptionsMenuComponent,
    ToastNotificationComponent,
    ToastContainerComponent,
    CreateCommentComponent,
    ShowPostWithCommentsComponent,
    CommentComponent,
    ReactionsComponent,
    UpdateCommentFormComponent,
    DeleteCommentDialogComponent,
    ReplyComponent,
    HeaderComponent,
    NavComponent,
    UserDropdownComponent,
    GroupPageComponent,
    ShowUserInGroupComponent,
    UserApproveGroupJoinComponent,
    ProfilePageComponent,
    SuspendGroupFormComponent,
    UpdateUserFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [
    ModalService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
