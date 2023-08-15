export async function process(upProcessSpinner: () => void, downProcessSpinner: () => void, func: () => void)
{
    upProcessSpinner();
    try {
        return await func();
    }
    catch (err) {
        return Promise.reject(err);
    }
    finally {
        downProcessSpinner();
    }
}

