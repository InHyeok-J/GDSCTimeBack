import * as UserRepository from '../../repositories/UserRepository';
import Local from './LocalStrategy';

export default (passport) => {
    passport.serializeUser((user, done) => {
        console.log(user);
        done(null, user);
    });
    passport.deserializeUser(async (user, done) => {
        //DB접근
        try {
            const finduser = await UserRepository.findUnique({ id: user.id });
            done(null, finduser);
        } catch (err) {
            console.error(err);
            done(err);
        }
    });
    //Strategy list..
    Local(passport);
};
