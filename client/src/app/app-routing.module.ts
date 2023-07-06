import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { GroupPageComponent } from './pages/group-page/group-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { AdminPanelComponent } from './pages/admin-panel/admin-panel.component';
import { RoleGuard } from './config/RoleGuard';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [RoleGuard],
    data: {
      allowedRoles: ['ADMIN', 'admin', 'USER', 'user'],
    },
  },
  {
    path: 'group/:id',
    component: GroupPageComponent,
    canActivate: [RoleGuard],
    data: {
      allowedRoles: ['ADMIN', 'admin', 'USER', 'user'],
    },
  },
  {
    path: 'profile/:id',
    component: ProfilePageComponent,
    canActivate: [RoleGuard],
    data: {
      allowedRoles: ['ADMIN', 'admin', 'USER', 'user'],
    },
  },
  {
    path: 'admin',
    component: AdminPanelComponent,
    canActivate: [RoleGuard],
    data: {
      allowedRoles: ['ADMIN', 'admin'],
    },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
