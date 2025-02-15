const Footer = () => {
    return (
        <footer>
            <section className="footer">
                <article className="footer-first">
                    <a className="footer-first-title" href="/">ОНЛАЙН МАГАЗИН</a>
                    <a className="footer-first-item" href="/">КОЗУНАЦИ</a>
                    <a className="footer-first-item" href="/">СЛАДКИ</a>
                    <a className="footer-first-item" href="/">ПОГАЧИ</a>
                    <a className="footer-first-item" href="/">ТОРТИ</a>
                    <a className="footer-first-item" href="/">БАНИЦИ</a>
                </article>
                <article className="footer-second">
                    <a className="footer-first-title" href="/">ЗА НАС</a>
                    <a className="footer-first-item" href="/">МАГАЗИН</a>
                    <a className="footer-first-item" href="/">НОВИНИ</a>
                    <a className="footer-first-item" href="/">КАРИЕРИ</a>
                    <a className="footer-first-item" href="/">КОНТАКТИ</a>
                    <a className="footer-first-item" href="/">ПРОГРАМИ</a>
                </article>
                <article className="footer-third">
                    <a className="footer-first-title" href="/">УСЛОВИЯ</a>
                    <a className="footer-first-item" href="/">УСЛОВИЯ ПОРЪЧКА И ДОСТАВКА</a>
                    <a className="footer-first-item" href="/">НАЧИН НА ПЛАЩАНЕ</a>
                    <a className="footer-first-item" href="/">ОБЩИ УСЛОВИЯ</a>
                    <a className="footer-first-item" href="/">ПРИЛОЖЕНИЕ ИЗПОЛЗВА БИСКИТИ</a>
                    <a className="footer-first-item" href="/">ПОМОЦИИ</a>
                </article>
                <article className="footer-four">
                    <a className="footer-first-title" href="/api/author" target="_blank">ЗА АВТОРА НА ПРИЛОЖЕНИЕТО</a>
                    <a className="footer-first-item" href="https://github.com/Ivzilol" target="_blank" rel="noreferrer">Github</a>
                    <a className="footer-first-item" href="https://www.linkedin.com/in/ivaylo-alichkov-7406871a1/" target="_blank" rel="noreferrer">Linkedin</a>
                    {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                    <a className="footer-first-item">Email: ivailoali@gmail.com</a>
                </article>
            </section>
            <hr className="footer-line"/>
            <p className="footer-author">© 2023 Ivaylo Alichkov</p>
        </footer>
    )
}
export default Footer;