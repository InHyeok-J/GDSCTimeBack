import * as UserRepository from '../repositories/UserRepository';
import bcrypt from 'bcrypt';
import catchAsync from '../utils/catchAsync';
import passport from 'passport';
import resFormat from '../utils/resFormat';

export const signUp = catchAsync(async (req, res) => {
    const existUserUserId = await UserRepository.findUnique({
        userId: req.body.userId,
    });

    if (existUserUserId) {
        return res
            .status(404)
            .send(resFormat.fail(404, '아이디가 이미 존재합니다'));
    }
    const existUserEmail = await UserRepository.findUnique({
        email: req.body.email,
    });
    if (existUserEmail) {
        return res
            .status(404)
            .send(resFormat.fail(404, '이메일이 이미 존재합니다'));
    }

    const existUserNicknmae = await UserRepository.findUnique({
        nickname: req.body.nickname,
    });

    if (existUserNicknmae) {
        return res
            .status(404)
            .send(resFormat.fail(404, '닉네임이 이미 존재합니다'));
    }

    req.body.password = await bcrypt.hash(req.body.password, 10);

    const user = await UserRepository.create(req.body);
    if (!user) {
        return res.status(500).send(resFormat.fail(500, '알수 없는 에러'));
    }
    delete user['password'];
    return res
        .status(200)
        .send(resFormat.successData(200, '회원가입성공', user));
});

export const login = catchAsync(async (req, res) => {
    passport.authenticate('local', (err, user, info) => {
        if (err) {
            throw new Error('에러');
        }
        if (!user) {
            return res.status(404).send(resFormat.fail(404, info.message));
        }
        req.login(user, (err) => {
            if (err) {
                throw new Error('패스포트 에러');
            }
            delete user['password'];
            return res
                .status(200)
                .send(resFormat.successData(200, '로그인성공', user));
        });
    })(req, res);
});

export const logout = catchAsync(async (req, res) => {
    req.logout();
    req.session.destroy((err) => {
        if (err) {
            throw new Error('세션 에러');
        } else {
            res.clearCookie('connect.sid');
            return res.status(200).send(resFormat.success(200, '로그아웃성공'));
        }
    });
});

export const getUser = catchAsync(async (req, res, next) => {
    if (req.user) {
        const exUser = await UserRepository.findUnique({ id: req.user.id });
        delete exUser['password'];
        return res
            .status(200)
            .send(resFormat.successData(200, '유저 정보 확인 성공', exUser));
    } else {
        return res.status(401).json(resFormat.fail(401, '유저 정보 확인 실패'));
    }
});
