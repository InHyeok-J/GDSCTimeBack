export const signup = (req, res, next) => {
    try {
        return 'signup';
    } catch (err) {
        console.error(err);
        next(err);
    }
};
