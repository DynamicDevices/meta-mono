import os

from oeqa.runtime.case import OERuntimeTestCase
from oeqa.core.decorator.depends import OETestDepends
from oeqa.core.decorator.oeid import OETestID
from oeqa.core.decorator.data import skipIfNotFeature
from oeqa.oetest import oeRuntimeTest, skipModule

class MonoCompileTest(OERuntimeTestCase):

    @classmethod
    def setUpClass(cls):

        files_dir = os.path.abspath(os.path.join(os.path.dirname(os.path.abspath(__file__)), "../files"))

        dst = '/tmp/'
        src = os.path.join(files_dir, 'helloworld.cs')
        cls.tc.target.copyTo(src, dst)
        src = os.path.join(files_dir, 'helloworldform.cs')
        cls.tc.target.copyTo(src, dst)
        src = os.path.join(files_dir, 'helloworldgtk.cs')
        cls.tc.target.copyTo(src, dst)

    @classmethod
    def tearDownClass(cls):
        files = '/tmp/helloworld.cs /tmp/helloworld.exe /tmp/helloworldform.cs /tmp/helloworldform.exe /tmp/helloworldgtk.cs /tmp/helloworldgtk.exe'
        cls.tc.target.run('rm %s' % files)

    @OETestDepends(['ssh.SSHTest.test_ssh'])
    def test_executable_compile_and_run_cmdline(self):
        status, output = self.target.run('mcs /tmp/helloworld.cs -out:/tmp/helloworld.exe')
        msg = 'mcs compile failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        status, output = self.target.run('mono /tmp/helloworld.exe')
        msg = 'running compiled file failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        self.assertEqual(output, 'HelloWorld', msg=msg)

    @OETestDepends(['ssh.SSHTest.test_ssh'])
    def test_executable_compile_and_run_winform(self):
#        if not oeRuntimeTest.hasFeature("x11"):
#          skipModule("No x11 feature in image")
        status, output = self.target.run('mcs /tmp/helloworldform.cs -out:/tmp/helloworldform.exe -r:System.Windows.Forms -r:System.Data -r:System.Drawing')
        msg = 'mcs compile failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        status, output = self.target.run('export DISPLAY=:0; mono /tmp/helloworldform.exe')
        msg = 'running compiled file failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)

    @OETestDepends(['ssh.SSHTest.test_ssh'])
    def test_executable_compile_and_run_gtk(self):
#        if not oeRuntimeTest.hasPackage("gtk-sharp"):
#          skipModule("No gtk-sharp package in image")
        status, output = self.target.run('mcs /tmp/helloworldgtk.cs -out:/tmp/helloworldgtk.exe -r:System.Windows.Forms -r:System.Data -r:System.Drawing -lib:/usr/lib/mono/gtk-sharp-2.0 -r:gtk-sharp -r:glib-sharp -r:atk-sharp')
        msg = 'mcs compile failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        status, output = self.target.run('export DISPLAY=:0; mono /tmp/helloworldgtk.exe')
        msg = 'running compiled file failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
