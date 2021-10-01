import express from 'express';
import * as AuthService from '../services/UserService';
import * as AuthHandler from '../middlewares/AuthHandler';

const router = express.Router();

router.get('/', AuthService.getUser);
router.post('/', AuthHandler.isNotLoggedIn, AuthService.signUp);
router.post('/login', AuthHandler.isNotLoggedIn, AuthService.login);
router.get('/logout', AuthHandler.isLoggedIn, AuthService.logout);

export default router;
