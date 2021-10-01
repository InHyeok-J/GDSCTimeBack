import express from 'express';
import * as AuthService from '../services/AuthService';

const router = express.Router();

router.get('/', AuthService.signup);

export default router;
