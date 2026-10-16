goog.module('router.fu.BrowserTest');
const Mode = goog.require('router.fu.browser.Mode');
const Probe = goog.require('router.fu.browser.BrowserProbe');
const TestCase = goog.require('goog.testing.TestCase');
const asserts = goog.require('goog.testing.asserts');
const testSuite = goog.require('goog.testing.testSuite');

/** @param {function():boolean} condition @return {!Promise<void>} */
async function waitFor(condition) {
  const deadline = Date.now() + 10000;
  while (!condition()) {
    if (Date.now() > deadline) throw new Error('Timed out waiting for routing');
    await new Promise(resolve => setTimeout(resolve, 20));
  }
}

testSuite({
  async testNativeRegexAndHashBackend() {
    asserts.assertEquals(Mode.environment === 'development', Probe.checksEnabled());
    Probe.start();
    try {
      await new Promise(resolve => setTimeout(resolve, 50));
      const initial = Probe.changes();
      Probe.change('/native/42');
      await waitFor(() => Probe.changes() > initial);
      asserts.assertEquals('/native/42', Probe.location());
      Probe.change('');
      await waitFor(() => Probe.location() === '');
      asserts.assertFalse(location.href.endsWith('#'));
      Probe.stop();
      await new Promise(resolve => setTimeout(resolve, 50));
      const stopped = Probe.changes();
      Probe.change('/detached');
      await new Promise(resolve => setTimeout(resolve, 100));
      asserts.assertEquals(stopped, Probe.changes());
    } finally {
      Probe.stop();
      history.replaceState('', '', location.pathname + location.search);
    }
  },

  async testGeneratedArezRouter() {
    const frame = /** @type {!HTMLIFrameElement} */ (document.createElement('iframe'));
    frame.src = '/example/example/index.html';
    document.body.appendChild(frame);
    try {
      /** @type {!Promise<void>} */
      const loaded = new Promise(resolve => { frame.onload = () => { resolve(undefined); }; });
      await loaded;
      const page = /** @type {!Document} */ (frame.contentDocument);
      const view = /** @type {!Window} */ (frame.contentWindow);
      /** @param {string} id @return {string} */
      const text = id => page.getElementById(id).textContent || '';
      /** @param {string} id @return {void} */
      const click = id => { /** @type {!HTMLElement} */ (page.getElementById(id)).click(); };
      await waitFor(() => text('location') === '/login');
      click('login');
      await waitFor(() => text('username') === 'Bob');
      click('route_regionEvent');
      await waitFor(() => text('location') === '/regions/ballarat/events/42');
      asserts.assertEquals('ballarat', text('regionCode'));
      asserts.assertEquals('42', text('eventId'));
      click('randomize_regionCode');
      await waitFor(() => text('regionCode') !== 'ballarat');
      asserts.assertEquals('/regions/' + text('regionCode') + '/events/42', text('location'));
      view.location.hash = '/regions/harbor/events/45';
      await waitFor(() => text('eventId') === '45');
      asserts.assertEquals('harbor', text('regionCode'));
      click('logout');
      await waitFor(() => text('location') === '/login');
      asserts.assertEquals('', text('username'));
      asserts.assertEquals('', text('regionCode'));
      asserts.assertEquals('', text('eventId'));
    } finally {
      frame.remove();
    }
  },
});
TestCase.getActiveTestCase().promiseTimeout = 30000;
